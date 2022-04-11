# Co to jest korutyna ?

Jest to zestaw komponentów które pozwalają na czasowe zawieszenie oraz późniejsze wznowienie
wykonywania fragmentu kodu.

1. W wątku głównym uruchamiany jest kosztowny kod (np pobieranie danych przez API)
2. Tworzony jest save point
3. Wątek główny jest cały czas wolny - może obsługiwać inne korutyny lub widok
4. Gdy w miejscu save pointu pojawią się dane korutyna czeka na wątek główny
5. Gdy wątek będzie wolny następuje wznowienie go od savepointu

Mechanizm zamieszania polega na zatrzymaniu wykonywania kodu w określonym miejscu.

# Sequence generator - przykład wykorzystania mechanizmu zawieszania

Sequence jest strukturą przypominającą kolekcje, jednak poszczególne jej elementy wyznaczane
są na żądanie, tylko w razie potrzeby. 

Generatory sekwencji wykorzystują mechanizm zawieszania. Kod zatrzymywany jest
w miejscu wywołania funkcji *yeld*, przy następnym odwołaniu sekwencji
kod jest odwieszany i wykounuje się jeszcze raz.

Mechanizm zawieszania powoduje że wykonywane są skoki pomiędzy wątkiem głównym
a generatorem sekwencji.

```kotlin
 @Test
    fun test() = runTest {
      val seq= sequence<Int> {
          //pętla nieskończona
          while (true){
              println("Generate item")
              //generujemy i zamrażamy
              yield(Random.nextInt(0,100))
          }
      }

        val it=seq.iterator()
        
        println("Number 1=>${it.next()}")
        println("Number 2=>${it.next()}")
        println("Number 3=>${it.next()}")

    }
```

```
Generate item
Number 1=>42
Generate item
Number 2=>99
Generate item
Number 3=>82
```

# Wsparcie natywne vs biblioteka kotlinx.coroutines

**Wsparcie natywne**
* Obejmuje zestaw mechanizmów niskopoziomowych, które dają programiście dużą swobodę wykorzystania
* Dostarcza elementów takich jak *suspendCoroutine* oraz *Continuation*
* Ich przenaczeniem jest wykorzystanie w bibliotekach a nie w kodzie biznesowym

**Wsparcie biblioteczne**
* Wymaga dostarczenia ich w formie osobnej dependency
* Zestaw mechanizmów wypracowanych zawierających dobre praktyki wykorzystania
* Przeznaczone do użycia w kodzie biznesowym

![](assets/img/nativesupport_vs_kotlin_coroutines.png)

## Przykłady użycia elementów natywnych

**Zawieszenie bieżącego wątku na 1 sekundę**

```kotlin
 @Test
    fun test2() = runTest {
        println("Before")
        //zawieszenie bieżącego wątku
        //w lambdzie kod który ma się wykonać na chwilę
        //PRZED zawieszeniem
        suspendCoroutine { cont: Continuation<Unit> ->
            //powołuję nowy wątek
            thread {
                //usypiam go na 1 s
                Thread.sleep(1000)
                //wątek się wybudza i wznawia korutynę
                cont.resume(Unit)
            }
        }
        println("After")
    }
```

## Przykłady użycia elementów bibliotecznych
