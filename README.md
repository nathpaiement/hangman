# Hangman

This is a java recreation of the classic game hangman, where the player tries to guess the word selected by the computer with a certain amount of lives.

The project started as an exercise in a software development course I attended in the winter semester of 2016-2017 where I started learning Java. Original game made in about a week.

Anyway, have fun with it and if you want to contribute (like adding a ASCII-art hangman), then feel free to do so :)

## RUNNING HANGMAN

- download Hangman.jar
- open a terminal where you downloaded Hangman.jar
- type: `java -jar Hangman.jar`
- **have fun!**

___
## PREVIEWS


### Welcome screen
```




        █▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀█
        █  HANGMAN - guess the word  █
        █▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄█



═══════════════════════════════
> Press [1] to start a new game
> Press [2] to show the scoreboard
> Press [3] to show the introduction
> Press [4] to quit

```


### Guessing screen
```



        Good guess!

        █▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀█
        █  5 letters | 4/8 lives left | Misses: O L M H  █
        █▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄█

         Make a guess: Y E A S _


```


### Win screen
```



        █▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀█
        █  YOU WON! The word was indeed: YEAST  █
        █▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄█
        █  Current score for this round: 10  █
        █▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄█

                 Guessing the word: 5
              Bonus for lives left: 5
                        Multiplier: 1
                             Total: 10 points

═══════════════════════════════════
> Press [1] to guess the next word!
> Press [2] to return to the main menu (points are saved)

```


### Scoreboard
```



        █▀▀▀▀▀▀▀▀▀▀▀▀▀▀█
        █  SCOREBOARD  █
        █▄▄▄▄▄▄▄▄▄▄▄▄▄▄█


> 80 points
> 66 points
> 32 points
══════════════════════════════════════
> Press [1] to return to the main menu

```