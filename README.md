# Alpha-Beta-pruing-and-Minimax-Algorithms-implementation-in-determining-next-move-for-game-Mancala

The rules of the Mancala game can be found at https://en.wikipedia.org/wiki/Mancala [1] and you can also try playing it online at
http://play-mancala.com/ [2] to get a better understanding of the game.

INPUT:
<Task#> Greedy=1, MiniMax=2, Alpha-Beta=3, Competition=4
<Your player: 1 or 2>
<Cutting off depth> (for greedy consider cutoff=1)
<Board state for player-2>
<Board state for player-1>
<#stones in player-2’s mancala>
<#stones in player-1’s mancala>

OUTPUT:
 1. next_state will show the board position after plthe next move.
 2. traverse_log in case of minimax will consists of following:
    Node,Depth,Value
    root,0,-Infinity
    B2,1,Infinity
    B3,1,Infinity
    A2,2,1
    B3,1,1
    A3,2,1
    .
    .
    .
    .
    .
3. traverse_log in case of alpha-beta pruning will will consists of following:
  Node,Depth,Value,Alpha,Beta
  .
  .
  .
  .
  .
  .
  .

