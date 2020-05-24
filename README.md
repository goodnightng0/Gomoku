# Gomoku
Gomoku implemented by Alpha-Beta pruning
## Program Goals
- Implement an alpha-beta search with iterative deepening search.
- There is a preset time limit for each move, e.g., 10 seconds. 
- The board has 19 × 19 positions.

## Implementation Results
<p align="center">
<img src="/results/result1.png" height="50%" width="50%" alt="Result #1"></img></p>
<p align="center">                                                                        
<img src="/results/result2.png" height="40%" width="40%" alt="Result #2"></img></p>
<p align="center">
<img src="/results/result3.png" height="30%" width="30%" alt="Result #3"></img></p>


## Implementation
The gomoku playing application I built consists of 4 classes.
-	Main
-	Play
-	Design
-	AlphaBeta

### Main
```
I have defined Black to be the first player. Therefore the user has to input which turn she will take.
Then we set the max time limit for our iterative deepening alpha beta search.
This will function as our main class.
```

## Play
```
We set who will make the first move and the max time limit for our search.
If the ai has to start, we place it in (10, 10) right in the center, which has the highest probability to win.
In and between, we check if there are any winners.( 2 for human and 1 for the computer, none for a tie)
We terminate the game if the alphabeta returns a null value which means that the board is full. 
```

## Design
```
Design constructs a matrix that we can use to keep track of our positions/generated moves.
Humans will have ‘x’ as our stone, ‘o’ for AI and ‘.’ for blank slots.
```

## AlphaBeta
```
Implement the AlphaBeta pruning search algorithm:
We calculate each of the players scores up until now by evaluating the conditions heuristically. 
(Examine how many stones are in a row horizontally, vertically and diagonally)
We first keep the depth as 1 as our initial value. If there is more time, we gradually increment the depth by one. 
Then, the alphabetasearch returns the best choice available.
Because it has to give the best move for AI, we set alpha as the maximum valued move for the AI agent.
```

## References
* [GeeksforGeeks](https://www.geeksforgeeks.org/minimax-algorithm-in-game-theory-set-4-alpha-beta-pruning/)
