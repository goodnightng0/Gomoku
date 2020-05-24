# Gomoku
Gomoku implemented by Alpha-Beta pruning
## Program Goals
- Implement an alpha-beta search with iterative deepening search.
- There is a preset time limit for each move, e.g., 10 seconds. 
- The board has 19 × 19 positions.
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
We first set who will make the first move and the max time limit for our search.
If the ai has to start, we place it in (10, 10) right in the center, which has the highest probability to win. Then, it is our turn. We take the next move as an input. By realMove(x,y) we place the stone in the right coordinates. For this, we use the addstone function in design class. If the slot is already taken, we do not make any changes.
In and between, we check if there are any winners. If winner is 2, it is a win for human. If 1, the computer wins and if we reach the end of the game without a winner, it is a win for neither.
If the alphabeta returns a null value, it means that the board is full and can not continue. We therefore terminate the game.
By the calculateScore function of alphabeta, we represent the scores that each White Player and Black player has. It is derived heuristically in our alphabeta algorithm.
```

## Design
```
For design, we actually keep our board design and the matrix. addStone actually adds the stones in every step. However, addStoneforAI does not actually place the stones on the board but keeps it temporarily for our alphabeta tree. We use it when we search through the tree when we consider the possible actions.
Design actually constructs a temporary matrix that we can use.
The arraylist actions keeps a list of generated moves for us. We use it later on in alpha beta search. It looks for cells that has at least one stone in an adjacent position. This is because we will have to maximize our score while preventing the other player from winning(putting as many stones in a row)
ShowBoard draws the board design for us. Humans will have ‘x’ as our stone, ‘o’ for AI and ‘.’ for blank slots.
```

## AlphaBeta
```
We implement the AlphaBeta pruning search algorithm
The scoreEvaluate function calculates each of the players scores up till now. It uses the calculateScore which examines how many stones are in a row horizontally, vertically and diagonally. It uses the getscore function above which evaluates the conditions heuristically. I have assigned points for each environments, having a higher value if it has more stones in a row.
calculateNextMove tells the AI agent which action it should choose next. Because we have chosen it to be an iterative deepening search, we save the current time in start time. We also check in advance if any available move can terminate the game.
We first keep the depth as 1 as our initial value. If there is more time, we gradually increment the depth by one. Then, the alphabetasearch returns the best choice available. Because it has to give the best move for AI, we set alpha as the maximum valued move for the AI agent. It returns the score, and move as a result.
Because if we use the boardMatrix it is confusing and hard to restore it, we make a copy of it and search through it. For this, we use the well known alpha beta pruning search.
If the player has a score that will win, we return it instantly.
```
