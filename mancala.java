[The following can be further optimized in terms of number of lines. As it was meant for university assignment, some part of the
code was written to get the ouptu in a particular format]

import java.io.*;
import java.util.*;

class case4
{
    
    //static int n=1;
    public int player,cutoff,board[][];
    public static int no_pits=0;
    public int[][] temp;
    public static int[][] answer;
    public static int answer_eval;
    String arr[]={"B","A"};
    
    public case4(int player,int cutoff,int board[][],int no_pits)throws IOException
    {
        this.player=player;
        this.cutoff=cutoff;
        this.board=board;
        this.no_pits=no_pits;
        temp=new int[2][board[0].length];
        answer=new int[2][no_pits+1];
        answer_eval=Integer.MIN_VALUE;
        
        
    }
    public String print(int a)
    {
        String b;
        if(a==Integer.MIN_VALUE)
            b="-Infinity";
        else
            if(a==Integer.MAX_VALUE)
                b="Infinity";
        else
                b=a+"";
        return b;
    }
    public void test()throws IOException//minimax
    {
        String node="root";
        int depth=0;
        //initializing my player and opposite player
        int my_player=player;
        int opposite_player;
        if(my_player==1)
            opposite_player=2;
        else
            opposite_player=1;
        //
        
        //next_move.txt
        BufferedWriter opt=null;
        File file=new File("next_state.txt");
        opt = new BufferedWriter(new FileWriter(file));
        
        //traverse_log.txt
        BufferedWriter opt1=null;
        File file1=new File("traverse_log.txt");
        opt1 = new BufferedWriter(new FileWriter(file1));
        
        opt1.write("Node,Depth,Value");
        opt1.newLine();
        int value=minimax(node,depth,board,player,true,0,false,arr,cutoff,my_player,opposite_player,opt1);
        
        for(int i=1;i>=0;i--)
        {
            for(int j=0;j<no_pits;j++)
                opt.write(answer[i][j]+" ");
            opt.newLine();
        }
        opt.write(answer[1][no_pits]+"");
        opt.newLine();
        opt.write(answer[0][no_pits]+"");
        
        opt.close();
        opt1.close();
        /*
        System.out.println();
        System.out.println("NEXT MOVE : ");
        for(int i=1;i>=0;i--)
        {
            for(int j=0;j<no_pits;j++)
                System.out.print(answer[i][j]+" ");
            System.out.println();
        }
        System.out.println(answer[1][no_pits]);
        System.out.println(answer[0][no_pits]);
        System.out.println("max val= "+value);
        System.out.println("$$$$$");
        */
    }
    public void test1()throws IOException //alpha beta
    {
        String node="root";
        int depth=0;
        //initializing my player and opposite player
        int my_player=player;
        int opposite_player;
        if(my_player==1)
            opposite_player=2;
        else
            opposite_player=1;
        
        //next_move.txt
        BufferedWriter opt=null;
        File file=new File("next_state.txt");
        opt = new BufferedWriter(new FileWriter(file));
        
        //traverse_log.txt
        BufferedWriter opt1=null;
        File file1=new File("traverse_log.txt");
        opt1 = new BufferedWriter(new FileWriter(file1));
        
        opt1.write("Node,Depth,Value,Alpha,Beta");
        opt1.newLine();
        
        int value=alpha_beta(node,depth,Integer.MIN_VALUE,Integer.MAX_VALUE,board,player,true,0,false,arr,cutoff,my_player,opposite_player,opt1);
        
        for(int i=1;i>=0;i--)
        {
            for(int j=0;j<no_pits;j++)
                opt.write(answer[i][j]+" ");
            opt.newLine();
        }
        opt.write(answer[1][no_pits]+"");
        opt.newLine();
        opt.write(answer[0][no_pits]+"");
        
        opt.close();
        opt1.close();
        
        //printing answer
        /*System.out.println();
        System.out.println("NEXT MOVE : ");
        for(int i=1;i>=0;i--)
        {
            for(int j=0;j<no_pits;j++)
                System.out.print(answer[i][j]+" ");
            System.out.println();
        }
        System.out.println(answer[1][no_pits]);
        System.out.println(answer[0][no_pits]);
        System.out.println("max val= "+value);
        System.out.println("$$$$$");*/
    }
    public void test2()throws Exception //greedy
    {
        String node="root";
        int depth=0;
        //initializing my player and opposite player
        int my_player=player;
        int opposite_player;
        if(my_player==1)
            opposite_player=2;
        else
            opposite_player=1;
        //
        int value=greedy(node,depth,board,player,true,0,false,arr,cutoff,my_player,opposite_player);
        
        //next_move.txt
        BufferedWriter opt=null;
        File file=new File("next_state.txt");
        opt = new BufferedWriter(new FileWriter(file));
        
        for(int i=1;i>=0;i--)
        {
            for(int j=0;j<no_pits;j++)
                opt.write(answer[i][j]+" ");
            opt.newLine();
        }
        opt.write(answer[1][no_pits]+"");
        opt.newLine();
        opt.write(answer[0][no_pits]+"");
        
        opt.close();
        
        /*
        System.out.println();
        System.out.println("NEXT MOVE : ");
        for(int i=1;i>=0;i--)
        {
            for(int j=0;j<no_pits;j++)
                System.out.print(answer[i][j]+" ");
            System.out.println();
        }
        System.out.println(answer[1][no_pits]);
        System.out.println(answer[0][no_pits]);
        System.out.println("max val= "+value);
        System.out.println("$$$$$");
        */
    }
    public int minimax(String node,int d_depth,int[][]board,int player,boolean max_player,int d_old_depth,boolean repeat_chance,String arr[],int cutoff,int my_player,int opposite_player,BufferedWriter opt1)throws IOException
    {
        String old_node=node;
        int depth=d_depth;
        int old_depth=d_old_depth;
        int value=0;
        //base condition
        //if depth==1 and repat_chance is true than take the deepest move in depth 1
        //check if all pits of my_player are empty
        int t;
        int check=player%2+1;
        for(t=0;t<no_pits;t++)
            if(board[check-1][t]!=0)
                break;
        
        if(t!=no_pits)
        {
            check=check%2+1;
            for(t=0;t<no_pits;t++)
                if(board[check-1][t]!=0)
                    break;
        }
        //System.out.println("T:"+t);
        if(t==no_pits)
        {
            check=check%2+1;
            for(int p=0;p<no_pits;p++)
            {
                board[check-1][no_pits]+=board[check-1][p];
                board[check-1][p]=0;
                
            }
            if(repeat_chance)
            {
                if(depth%2==0)
                {
                    opt1.write(old_node+","+depth+","+"Infinity");
                    opt1.newLine();
                }
                else{
                    opt1.write(old_node+","+depth+","+"-Infinity");
                    opt1.newLine();
                }
            }
            else if(depth!=cutoff)
            {
                if(depth%2==0){
                    opt1.write(old_node+","+depth+","+"-Infinity");
                    opt1.newLine();
                }
                else{
                    opt1.write(old_node+","+depth+","+"Infinity");
                    opt1.newLine();
                }
            }
            
            opt1.write(old_node+","+depth+","+(board[my_player-1][no_pits]-board[opposite_player-1][no_pits]));
            opt1.newLine();
            return board[my_player-1][no_pits]-board[opposite_player-1][no_pits];
        }
        //
        
        
        if(depth==0 && old_depth==0){
            opt1.write(node+","+depth+","+"-Infinity");
            opt1.newLine();
        }
        else if(depth!=cutoff && depth%2==1 && (depth-1)==old_depth)
            {
                if(!repeat_chance){
                    opt1.write(node+","+depth+","+"Infinity");
                    opt1.newLine();
                }
                else
                {
                    opt1.write(node+","+depth+","+"-Infinity");
                    opt1.newLine();
                }
            }
        else if(depth!=cutoff && depth%2==0 && (depth-1)==old_depth)
            {
                if(!repeat_chance){
                    opt1.write(node+","+depth+","+"-Infinity");
                    opt1.newLine();
                }
                else{
                    opt1.write(node+","+depth+","+"Infinity");
                    opt1.newLine();
                }
            }
        else if(depth==cutoff && repeat_chance)
        {
            if(depth%2==1){
                opt1.write(node+","+depth+","+"-Infinity");
                opt1.newLine();
            }
            else{
                opt1.write(node+","+depth+","+"Infinity");
                opt1.newLine();
            }
        }
        else if(depth==cutoff && !repeat_chance)
            {
                opt1.write(node+","+depth+","+(board[my_player-1][no_pits]-board[opposite_player-1][no_pits]));
                opt1.newLine();
                return board[my_player-1][no_pits]-board[opposite_player-1][no_pits];
            }
        
        // 
        if(max_player)
        {
            int[][]temp=new int[2][no_pits+1];
            value=Integer.MIN_VALUE;
            for(int pit=0;pit<no_pits;pit++)
            {
                if(board[player-1][pit]!=0)
                {
                    /*String old_node=node;
                    //System.out.println("////////////////////////////////////////////////////");
                    //System.out.println("old node : "+old_node);
                    for(int p=1;p>=0;p--)
                    {
                        for(int q=0;q<no_pits+1;q++)
                            System.out.print(board[p][q]+" ");
                        System.out.println();
                    }
                    */
                    node=arr[player-1]+""+(pit+2); //copy pit that is to be emptied now 
                    for(int i=0;i<2;i++)           //copy board setting into new array to be dealt with
                        for(int j=0;j<no_pits+1;j++)
                            temp[i][j]=board[i][j];
                    
                    //empty the stones in that pit and determine where the last stone is placed
                    int pit_side=player;
                    int pointer=pit;
                    int pointer_side[]=indexOf_lastStone(temp,player,pit_side,pointer,pit);
                    //System.out.println(pointer_side[0]+" "+player+" "+temp[player-1][pointer_side[0]]);
                    //check where the position of last stone placed and accordingly give a recursive call
                    int next_player;
                    if(player==1)
                        next_player=2;
                    else
                        next_player=1;
                    
                    if(pointer_side[0]>=0 && pointer_side[0]<no_pits && pointer_side[1]==player && temp[player-1][pointer_side[0]]==1)
                    {
                        temp[player-1][no_pits]+=temp[0][pointer_side[0]]+temp[1][pointer_side[0]];
                        temp[0][pointer_side[0]]=0;
                        temp[1][pointer_side[0]]=0;
                        
                        /*System.out.println("case 1 : temp array-------------");
                        for(int x=1;x>=0;x--)
                        {
                            for(int y=0;y<no_pits+1;y++)
                                System.out.print(temp[x][y]+" ");
                            System.out.println();
                        }*/
                        //int val;
                        int val;
                        if(depth%2==0)
                            val=minimax(node, depth+1, temp, next_player, false, depth, false, arr,cutoff,my_player,opposite_player,opt1);
                        else
                            val=minimax(node, depth, temp, next_player, false, old_depth, false, arr,cutoff,my_player,opposite_player,opt1);
                        
                        //checking if depth==0 and val is max so as to take that as next move
                        if((depth==0 || depth==1) && val>answer_eval)
                        {
                            answer_eval=val;
                            for(int i=0;i<2;i++)
                                for(int j=0;j<no_pits+1;j++)
                                    answer[i][j]=temp[i][j];
                        }
                        //
                        value=Math.max(value, val);
                        opt1.write(old_node+","+depth+","+value);
                        opt1.newLine();
                    }
                    else if(pointer_side[0]>=0 && pointer_side[0]<no_pits) //in such a case next player plays
                    {
                        /*System.out.println("case 2 : temp array-------------");
                        for(int x=1;x>=0;x--)
                        {
                            for(int y=0;y<no_pits+1;y++)
                                System.out.print(temp[x][y]+" ");
                            System.out.println();
                        }*/
                        int val;
                        if(depth%2==0 )
                            val=minimax(node, depth+1, temp, next_player, false, depth, false, arr,cutoff,my_player,opposite_player,opt1);
                        else
                            val=minimax(node, depth, temp, next_player, false, old_depth, false, arr,cutoff,my_player,opposite_player,opt1);
                        
                        //checking if depth==0 and val is max so as to take that as next move
                        if((depth==0 || depth==1) && val>answer_eval)
                        {
                            answer_eval=val;
                            for(int i=0;i<2;i++)
                                for(int j=0;j<no_pits+1;j++)
                                    answer[i][j]=temp[i][j];
                        }
                        //
                        value=Math.max(value, val);
                        opt1.write(old_node+","+depth+","+value);
                        opt1.newLine();
                    }
                    else if(pointer_side[0]==no_pits) //player gets another chance
                    {
                        /*System.out.println("case 3 : temp array-------------");
                        for(int x=1;x>=0;x--)
                        {
                            for(int y=0;y<no_pits+1;y++)
                                System.out.print(temp[x][y]+" ");
                            System.out.println();
                        }*/
                        int val;
                        if(depth%2==0)
                            val=minimax(node, depth+1, temp, player, true, depth, true, arr,cutoff,my_player,opposite_player,opt1);
                        else
                            val=minimax(node, depth, temp, player, true, old_depth, true, arr,cutoff,my_player,opposite_player,opt1);
                        repeat_chance=true;
                        //checking if depth==0 and val is max so as to take that as next move
                        if((depth==0 || depth==1) && val>answer_eval)
                        {
                            answer_eval=val;
                            for(int i=0;i<2;i++)
                                for(int j=0;j<no_pits+1;j++)
                                    answer[i][j]=temp[i][j];
                        }
                        //
                        value=Math.max(value, val);
                        opt1.write(old_node+","+depth+","+value);
                        opt1.newLine();
                    }
                }
            }
            return value;
        }
        if(!max_player)
        {
            int[][]temp=new int[2][no_pits+1];
            value=Integer.MAX_VALUE; 
            for(int pit=0;pit<no_pits;pit++)
            {
                if(board[player-1][pit]!=0)
                {
                    /*String old_node=node;
                    System.out.println("////////////////////////////////////////////////////");
                    System.out.println("old node : "+old_node);
                    //
                    for(int p=1;p>=0;p--)
                    {
                        for(int q=0;q<no_pits+1;q++)
                            System.out.print(board[p][q]+" ");
                        System.out.println();
                    }
                    */
                    node=arr[player-1]+""+(pit+2); //copy pit that is to be emptied now 
                    for(int i=0;i<2;i++)           //copy board setting into new array to be dealt with
                        for(int j=0;j<no_pits+1;j++)
                            temp[i][j]=board[i][j];
                    
                    //empty the stones in that pit and determine where the last stone is placed
                    int pit_side=player;
                    int pointer=pit;
                    int pointer_side[]=indexOf_lastStone(temp,player,pit_side,pointer,pit);
                    //System.out.println(pointer_side[0]+" "+player+" "+temp[player-1][pointer_side[0]]);
                    //check where the position of last stone placed and accordingly give a recursive call
                    int next_player;
                    if(player==1)
                        next_player=2;
                    else
                        next_player=1;

                    if(pointer_side[0]>=0 && pointer_side[0]<no_pits && pointer_side[1]==player && temp[player-1][pointer_side[0]]==1)
                    {
                        /*System.out.println("case 1 : temp array-------------");
                        for(int x=1;x>=0;x--)
                        {
                            for(int y=0;y<no_pits+1;y++)
                                System.out.print(temp[x][y]+" ");
                            System.out.println();
                        }*/
                        temp[player-1][no_pits]+=temp[0][pointer_side[0]]+temp[1][pointer_side[0]];
                        temp[0][pointer_side[0]]=0;
                        temp[1][pointer_side[0]]=0;
                        int val;
                        if(depth%2==1)
                            val=minimax(node, depth+1, temp, next_player, true, depth, false, arr,cutoff,my_player,opposite_player,opt1);
                        else
                            val=minimax(node, depth, temp, next_player, true, old_depth, false, arr,cutoff,my_player,opposite_player,opt1);
                        value=Math.min(value, val);
                        opt1.write(old_node+","+depth+","+value);
                        opt1.newLine();
                    }
                    else if(pointer_side[0]>=0 && pointer_side[0]<no_pits) //in such a case next player plays
                    {
                        /*System.out.println("case 2 : temp array-------------");
                        for(int x=1;x>=0;x--)
                        {
                            for(int y=0;y<no_pits+1;y++)
                                System.out.print(temp[x][y]+" ");
                            System.out.println();
                        }*/
                        int val;
                        if(depth%2==1)
                            val=minimax(node, depth+1, temp, next_player, true, depth, false, arr,cutoff,my_player,opposite_player,opt1);
                        else
                            val=minimax(node, depth, temp, next_player, true, old_depth, false, arr,cutoff,my_player,opposite_player,opt1);
                        value=Math.min(value, val);
                        opt1.write(old_node+","+depth+","+value);
                        opt1.newLine();
                    }
                    else if(pointer_side[0]==no_pits) //player gets another chance
                    {
                        /*System.out.println("case 3 : temp array-------------");
                        for(int x=1;x>=0;x--)
                        {
                            for(int y=0;y<no_pits+1;y++)
                                System.out.print(temp[x][y]+" ");
                            System.out.println();
                        }*/
                        int val;
                        if(depth%2==1)
                            val=minimax(node, depth+1, temp, player, false, depth, true, arr,cutoff,my_player,opposite_player,opt1);
                        else
                            val=minimax(node, depth, temp, player, false, old_depth, true, arr,cutoff,my_player,opposite_player,opt1);
                        value=Math.min(value, val);
                        opt1.write(old_node+","+depth+","+value);
                        opt1.newLine();
                    }
                }
            }
            return value;
        }
        return value;
    }
    public int greedy(String node,int d_depth,int[][]board,int player,boolean max_player,int d_old_depth,boolean repeat_chance,String arr[],int cutoff,int my_player,int opposite_player)
    {
        //String old_node=node;
        int depth=d_depth;
        int old_depth=d_old_depth;
        int value=0;
        //base condition
        //if depth==1 and repat_chance is true than take the deepest move in depth 1
        //check if all pits of my_player are empty
        int t;
        int check=player%2+1;
        for(t=0;t<no_pits;t++)
            if(board[check-1][t]!=0)
                break;
        
        if(t!=no_pits)
        {
            check=check%2+1;
            for(t=0;t<no_pits;t++)
                if(board[check-1][t]!=0)
                    break;
        }
        //System.out.println("T:"+t);
        if(t==no_pits)
        {
            check=check%2+1;
            for(int p=0;p<no_pits;p++)
            {
                board[check-1][no_pits]+=board[check-1][p];
                board[check-1][p]=0;
                
            }
            
            //System.out.println(old_node+","+depth+","+(board[my_player-1][no_pits]-board[opposite_player-1][no_pits])+" "+n++);
            return board[my_player-1][no_pits]-board[opposite_player-1][no_pits];
        }
        //
        
        
        /*if(depth==0 && old_depth==0)
            System.out.println(node+","+depth+","+"-Infinity");
        else if(depth!=cutoff && depth%2==1 && (depth-1)==old_depth)
            {
                if(!repeat_chance)
                    System.out.println(node+","+depth+","+"Infinity");
                else
                    System.out.println(node+","+depth+","+"-Infinity");
            }
        else if(depth!=cutoff && depth%2==0 && (depth-1)==old_depth)
            {
                if(!repeat_chance)
                    System.out.println(node+","+depth+","+"-Infinity");
                else
                    System.out.println(node+","+depth+","+"Infinity");
            }
        else if(depth==cutoff && repeat_chance)
        {
            if(depth%2==1)
                System.out.println(node+","+depth+","+"-Infinity");
            else
                System.out.println(node+","+depth+","+"Infinity");
        }*/
            if(depth==cutoff && !repeat_chance)
            {
                //System.out.println(node+","+depth+","+(board[my_player-1][no_pits]-board[opposite_player-1][no_pits]));
                return board[my_player-1][no_pits]-board[opposite_player-1][no_pits];
            }
        
        // 
        if(max_player)
        {
            int[][]temp=new int[2][no_pits+1];
            value=Integer.MIN_VALUE;
            for(int pit=0;pit<no_pits;pit++)
            {
                if(board[player-1][pit]!=0)
                {
                    /*String old_node=node;
                    //System.out.println("////////////////////////////////////////////////////");
                    //System.out.println("old node : "+old_node);
                    for(int p=1;p>=0;p--)
                    {
                        for(int q=0;q<no_pits+1;q++)
                            System.out.print(board[p][q]+" ");
                        System.out.println();
                    }
                    */
                    node=arr[player-1]+""+(pit+2); //copy pit that is to be emptied now 
                    for(int i=0;i<2;i++)           //copy board setting into new array to be dealt with
                        for(int j=0;j<no_pits+1;j++)
                            temp[i][j]=board[i][j];
                    
                    //empty the stones in that pit and determine where the last stone is placed
                    int pit_side=player;
                    int pointer=pit;
                    int pointer_side[]=indexOf_lastStone(temp,player,pit_side,pointer,pit);
                    //System.out.println(pointer_side[0]+" "+player+" "+temp[player-1][pointer_side[0]]);
                    //check where the position of last stone placed and accordingly give a recursive call
                    int next_player;
                    if(player==1)
                        next_player=2;
                    else
                        next_player=1;
                    
                    if(pointer_side[0]>=0 && pointer_side[0]<no_pits && pointer_side[1]==player && temp[player-1][pointer_side[0]]==1)
                    {
                        temp[player-1][no_pits]+=temp[0][pointer_side[0]]+temp[1][pointer_side[0]];
                        temp[0][pointer_side[0]]=0;
                        temp[1][pointer_side[0]]=0;
                        
                        /*System.out.println("case 1 : temp array-------------");
                        for(int x=1;x>=0;x--)
                        {
                            for(int y=0;y<no_pits+1;y++)
                                System.out.print(temp[x][y]+" ");
                            System.out.println();
                        }*/
                        //int val;
                        int val;
                        if(depth%2==0)
                            val=greedy(node, depth+1, temp, next_player, false, depth, false, arr,cutoff,my_player,opposite_player);
                        else
                            val=greedy(node, depth, temp, next_player, false, old_depth, false, arr,cutoff,my_player,opposite_player);
                        
                        //checking if depth==0 and val is max so as to take that as next move
                        if((depth==0 || depth==1) && val>answer_eval)
                        {
                            answer_eval=val;
                            for(int i=0;i<2;i++)
                                for(int j=0;j<no_pits+1;j++)
                                    answer[i][j]=temp[i][j];
                        }
                        //
                        value=Math.max(value, val);
                        //System.out.println(old_node+","+depth+","+value);
                    }
                    else if(pointer_side[0]>=0 && pointer_side[0]<no_pits) //in such a case next player plays
                    {
                        /*System.out.println("case 2 : temp array-------------");
                        for(int x=1;x>=0;x--)
                        {
                            for(int y=0;y<no_pits+1;y++)
                                System.out.print(temp[x][y]+" ");
                            System.out.println();
                        }*/
                        int val;
                        if(depth%2==0 )
                            val=greedy(node, depth+1, temp, next_player, false, depth, false, arr,cutoff,my_player,opposite_player);
                        else
                            val=greedy(node, depth, temp, next_player, false, old_depth, false, arr,cutoff,my_player,opposite_player);
                        
                        //checking if depth==0 and val is max so as to take that as next move
                        if((depth==0 || depth==1) && val>answer_eval)
                        {
                            answer_eval=val;
                            for(int i=0;i<2;i++)
                                for(int j=0;j<no_pits+1;j++)
                                    answer[i][j]=temp[i][j];
                        }
                        //
                        value=Math.max(value, val);
                        //System.out.println(old_node+","+depth+","+value);
                    }
                    else if(pointer_side[0]==no_pits) //player gets another chance
                    {
                        /*System.out.println("case 3 : temp array-------------");
                        for(int x=1;x>=0;x--)
                        {
                            for(int y=0;y<no_pits+1;y++)
                                System.out.print(temp[x][y]+" ");
                            System.out.println();
                        }*/
                        int val;
                        if(depth%2==0)
                            val=greedy(node, depth+1, temp, player, true, depth, true, arr,cutoff,my_player,opposite_player);
                        else
                            val=greedy(node, depth, temp, player, true, old_depth, true, arr,cutoff,my_player,opposite_player);
                        repeat_chance=true;
                        //checking if depth==0 and val is max so as to take that as next move
                        if((depth==0 || depth==1) && val>answer_eval)
                        {
                            answer_eval=val;
                            for(int i=0;i<2;i++)
                                for(int j=0;j<no_pits+1;j++)
                                    answer[i][j]=temp[i][j];
                        }
                        //
                        value=Math.max(value, val);
                        //System.out.println(old_node+","+depth+","+value);
                    }
                }
            }
            return value;
        }
        
        return value;
    }
    public int alpha_beta(String node,int d_depth,int alpha,int beta,int[][]board,int player,boolean max_player,int d_old_depth,boolean repeat_chance,String arr[],int cutoff,int my_player,int opposite_player,BufferedWriter opt1)throws IOException
    {
        
        String old_node=node;
        //String[]ab=new String[2];
        int depth=d_depth;
        int old_depth=d_old_depth;
     
        
        //using conversion from Integer.MAX_VALUE(Integer.MIN_VALUE) ---->   -Infinity(+Infinity)
        String print_alpha="";
        String print_beta="";
        if(alpha==Integer.MIN_VALUE)
            print_alpha="-Infinity";
        else
            print_alpha=alpha+"";
        if(beta==Integer.MAX_VALUE)
            print_beta="Infinity";
        else
            print_beta=beta+"";
        //
        
        //if any side pits are empty in the board ---> game over
        int t;
        int check=player%2+1;
        for(t=0;t<no_pits;t++)
            if(board[check-1][t]!=0)
                break;
        
        if(t!=no_pits)
        {
            check=check%2+1;
            for(t=0;t<no_pits;t++)
                if(board[check-1][t]!=0)
                    break;
        }
        //System.out.println("T:"+t);
        if(t==no_pits)
        {
            check=check%2+1;
            for(int p=0;p<no_pits;p++)
            {
                board[check-1][no_pits]+=board[check-1][p];
                board[check-1][p]=0;
            }
            if(repeat_chance)
            {
                if(depth%2==0)
                {
                    opt1.write(old_node+","+depth+","+"Infinity"+","+print_alpha+","+print_beta);
                    opt1.newLine();
                }
                else{
                    opt1.write(old_node+","+depth+","+"-Infinity"+","+print_alpha+","+print_beta);
                    opt1.newLine();
                }
            }
            else if(depth!=cutoff)
            {
                if(depth%2==0){
                    opt1.write(old_node+","+depth+","+"-Infinity"+","+print_alpha+","+print_beta);
                    opt1.newLine();
                }
                else{
                    opt1.write(old_node+","+depth+","+"Infinity"+","+print_alpha+","+print_beta);
                    opt1.newLine();
                }
            }
            
            opt1.write(old_node+","+depth+","+(board[my_player-1][no_pits]-board[opposite_player-1][no_pits])+","+print_alpha+","+print_beta);
            opt1.newLine();
            return board[my_player-1][no_pits]-board[opposite_player-1][no_pits];
        }
        //
        
        //base conditions
        //System.out.println("succ"+depth+" "+old_depth);
        if(depth==0 && old_depth==0){
            opt1.write(node+","+depth+","+"-Infinity"+","+print_alpha+","+print_beta);
            opt1.newLine();
        }
        else if(depth!=cutoff && depth%2==1 && (depth-1)==old_depth)
        {
            if(!repeat_chance){
                opt1.write(node+","+depth+","+"Infinity"+","+print_alpha+","+print_beta);
                opt1.newLine();
            }
            else{
                opt1.write(node+","+depth+","+"-Infinity"+","+print_alpha+","+print_beta);
                opt1.newLine();
            }
        }
            else if(depth!=cutoff && depth%2==0 && (depth-1)==old_depth)
            {
                if(!repeat_chance){
                    opt1.write(node+","+depth+","+"-Infinity"+","+print_alpha+","+print_beta);
                    opt1.newLine();
                }
                else{
                    opt1.write(node+","+depth+","+"Infinity"+","+print_alpha+","+print_beta);
                    opt1.newLine();
                }
            }
            else if(depth==cutoff && repeat_chance)
            {
                if(depth%2==1){
                    opt1.write(node+","+depth+","+"-Infinity"+","+print_alpha+","+print_beta);
                    opt1.newLine();
                }
                else{
                    opt1.write(node+","+depth+","+"Infinity"+","+print_alpha+","+print_beta);
                    opt1.newLine();
                }
            }
                else if(depth==cutoff && !repeat_chance)
            {
                opt1.write(node+","+depth+","+(board[my_player-1][no_pits]-board[opposite_player-1][no_pits])+","+print_alpha+","+print_beta);
                opt1.newLine();
                return board[my_player-1][no_pits]-board[opposite_player-1][no_pits];
            }
        //
        
        if(max_player)
        {
            int val=0;
            int[][]temp=new int[2][no_pits+1];
            int value=Integer.MIN_VALUE;
            for(int pit=0;pit<no_pits;pit++)
            {
                if(board[player-1][pit]!=0)
                {
                    //System.out.println("////////////////////////////////////////////////////");
                    //System.out.println("old node : "+old_node);
                    //
                    /*for(int p=1;p>=0;p--)
                    {
                        for(int q=0;q<no_pits+1;q++)
                            System.out.print(board[p][q]+" ");
                        System.out.println();
                    }*/
                    //
                    node=arr[player-1]+""+(pit+2); //copy pit that is to be emptied now 
                    for(int i=0;i<2;i++)           //copy board setting into new array to be dealt with
                        for(int j=0;j<no_pits+1;j++)
                            temp[i][j]=board[i][j];
                    //
                    //empty the stones in that pit and determine where the last stone is placed
                    int pit_side=player;
                    int pointer=pit;
                    int pointer_side[]=indexOf_lastStone(temp,player,pit_side,pointer,pit);
                    //System.out.println(pointer_side[0]+" "+player+" "+temp[player-1][pointer_side[0]]);
                    //check where the position of last stone placed and accordingly give a recursive call
                    int next_player;
                    if(player==1)
                        next_player=2;
                    else
                        next_player=1;
                    
                    if(pointer_side[0]>=0 && pointer_side[0]<no_pits && pointer_side[1]==player && temp[player-1][pointer_side[0]]==1)
                    {
                        //System.out.println("case 1 : temp array-------------");
                        temp[player-1][no_pits]+=temp[0][pointer_side[0]]+temp[1][pointer_side[0]];
                        temp[0][pointer_side[0]]=0;
                        temp[1][pointer_side[0]]=0;
                        //
                        /*for(int x=1;x>=0;x--)
                        {
                            for(int y=0;y<no_pits+1;y++)
                                System.out.print(temp[x][y]+" ");
                            System.out.println();
                        }*/
                        //int val;
                        //int old_alpha=alpha;
                        //System.out.println("depth : "+d_depth);
                        if(depth%2==0)
                            val=alpha_beta(node, depth+1, alpha, beta, temp, next_player, false, depth, false, arr, cutoff, my_player, opposite_player,opt1);
                        else
                            val=alpha_beta(node, depth, alpha, beta, temp, next_player, false, old_depth, false, arr, cutoff, my_player, opposite_player,opt1);
                           
                        //System.out.println("old_node : "+old_node+" value : "+value);
                        value=Math.max(value,val);
                        //checking if depth==0 and val is max so as to take that as next move
                        if((depth==0 || depth==1) && value>answer_eval)
                        {
                            answer_eval=value;
                            for(int i=0;i<2;i++)
                                for(int j=0;j<no_pits+1;j++)
                                    answer[i][j]=temp[i][j];
                        }
                        //
                        //System.out.println(old_node+","+depth+","+print(alpha)+","+print(alpha)+","+print(beta)+" "+n++);
                        if(value>=beta)
                        {
                            opt1.write(old_node+","+depth+","+print(value)+","+print(alpha)+","+print(beta));
                            opt1.newLine();
                            return value;
                        }
                        else
                        {
                            alpha=Math.max(alpha,value);
                            opt1.write(old_node+","+depth+","+print(value)+","+print(alpha)+","+print(beta));
                            opt1.newLine();
                        }
                    }
                    else if(pointer_side[0]>=0 && pointer_side[0]<no_pits)
                    {
                        /*System.out.println("case 2 : temp array-------------");
                        for(int x=1;x>=0;x--)
                        {
                            for(int y=0;y<no_pits+1;y++)
                                System.out.print(temp[x][y]+" ");
                            System.out.println();
                        }*/
                        //int val;
                        //int old_alpha=alpha;
                        //System.out.println("depth : "+d_depth);
                        if(depth%2==0)
                            val=alpha_beta(node, depth+1, alpha, beta, temp, next_player, false, depth, false, arr, cutoff, my_player, opposite_player,opt1);
                        else
                            val=alpha_beta(node, depth, alpha, beta, temp, next_player, false, old_depth, false, arr, cutoff, my_player, opposite_player,opt1);
                        //System.out.println(old_node+","+depth+","+print(alpha)+","+print(alpha)+","+print(beta)+" "+n++);
                        //System.out.println("old_node : "+old_node+" value : "+value);
                        value=Math.max(value,val);
                        //checking if depth==0 and val is max so as to take that as next move
                        if((depth==0 || depth==1) && value>answer_eval)
                        {
                            answer_eval=value;
                            for(int i=0;i<2;i++)
                                for(int j=0;j<no_pits+1;j++)
                                    answer[i][j]=temp[i][j];
                        }
                        //
                        if(value>=beta)
                        {
                            opt1.write(old_node+","+depth+","+print(value)+","+print(alpha)+","+print(beta));
                            opt1.newLine();
                            return value;
                        }
                        else
                        {
                            alpha=Math.max(alpha,value);
                            opt1.write(old_node+","+depth+","+print(value)+","+print(alpha)+","+print(beta));
                            opt1.newLine();
                        }
                    }
                    else if(pointer_side[0]==no_pits)
                    {
                        /*System.out.println("case 3 : temp array-------------");
                        for(int x=1;x>=0;x--)
                        {
                            for(int y=0;y<no_pits+1;y++)
                                System.out.print(temp[x][y]+" ");
                            System.out.println();
                        }*/
                        //int val;
                        int old_alpha=alpha;
                        //System.out.println("depth : "+d_depth);
                        if(depth%2==0)
                            val=alpha_beta(node, depth+1, alpha, beta, temp, player, true, depth, true, arr, cutoff, my_player, opposite_player,opt1);
                        else
                            val=alpha_beta(node, depth, alpha, beta, temp, player, true, old_depth, true, arr, cutoff, my_player, opposite_player,opt1);
                        //System.out.println("old_node : "+old_node+" value : "+value);
                        value=Math.max(val,value);
                        repeat_chance=true;
                        //System.out.println(old_node+","+depth+","+print(alpha)+","+print(alpha)+","+print(beta)+" "+n++);
               
                        //checking if depth==0 and val is max so as to take that as next move
                        if((depth==0 || depth==1) && value>answer_eval)
                        {
                            answer_eval=value;
                            for(int i=0;i<2;i++)
                                for(int j=0;j<no_pits+1;j++)
                                    answer[i][j]=temp[i][j];
                        }
                        //
                        if(value>=beta)
                        {
                            opt1.write(old_node+","+depth+","+print(value)+","+print(alpha)+","+print(beta));
                            opt1.newLine();
                            return value;
                        }
                        else
                        {
                            alpha=Math.max(alpha,value);
                            opt1.write(old_node+","+depth+","+print(value)+","+print(alpha)+","+print(beta));
                            opt1.newLine();
                        }
                    }
                   
                }
            }
            return value;
        }
        if(!max_player)
        {
            int val=0;
            int[][]temp=new int[2][no_pits+1];
            int value=Integer.MAX_VALUE;
            for(int pit=0;pit<no_pits;pit++)
            {
                if(board[player-1][pit]!=0)
                {
                    //System.out.println("////////////////////////////////////////////////////");
                    //System.out.println("old node : "+old_node);
                    //
                    /*for(int p=1;p>=0;p--)
                    {
                        for(int q=0;q<no_pits+1;q++)
                            System.out.print(board[p][q]+" ");
                        System.out.println();
                    }*/
                    //
                    node=arr[player-1]+""+(pit+2); //copy pit that is to be emptied now 
                    for(int i=0;i<2;i++)           //copy board setting into new array to be dealt with
                        for(int j=0;j<no_pits+1;j++)
                            temp[i][j]=board[i][j];
                    //
                    //empty the stones in that pit and determine where the last stone is placed
                    int pit_side=player;
                    int pointer=pit;
                    int pointer_side[]=indexOf_lastStone(temp,player,pit_side,pointer,pit);
                    //System.out.println(pointer_side[0]+" "+player+" "+temp[player-1][pointer_side[0]]);
                    //check where the position of last stone placed and accordingly give a recursive call
                    int next_player;
                    if(player==1)
                        next_player=2;
                    else
                        next_player=1;
                    
                    if(pointer_side[0]>=0 && pointer_side[0]<no_pits && pointer_side[1]==player && temp[player-1][pointer_side[0]]==1)
                    {
                        //System.out.println("case 1 : temp array-------------");
                        temp[player-1][no_pits]+=temp[0][pointer_side[0]]+temp[1][pointer_side[0]];
                        temp[0][pointer_side[0]]=0;
                        temp[1][pointer_side[0]]=0;
                        //
                        /*for(int x=1;x>=0;x--)
                        {
                            for(int y=0;y<no_pits+1;y++)
                                System.out.print(temp[x][y]+" ");
                            System.out.println();
                        }*/
                        //int val;
                        int old_beta=beta;
                        //System.out.println("depth : "+d_depth);
                        if(depth%2==1)
                            val=alpha_beta(node, depth+1, alpha, beta, temp, next_player, true, depth, false, arr, cutoff, my_player, opposite_player,opt1);
                        else
                            val=alpha_beta(node, depth, alpha, beta, temp, next_player, true, old_depth, false, arr, cutoff, my_player, opposite_player,opt1);
                        //System.out.println("old_node : "+old_node+" value : "+value);
                        value=Math.min(value,val);
                        if(value<=alpha)
                        {
                            opt1.write(old_node+","+depth+","+print(value)+","+print(alpha)+","+print(beta));
                            opt1.newLine();
                            return value;
                        }
                        else
                        {
                            beta=Math.min(beta,value);
                            opt1.write(old_node+","+depth+","+print(value)+","+print(alpha)+","+print(beta));
                            opt1.newLine();
                        }
                    }
                    else if(pointer_side[0]>=0 && pointer_side[0]<no_pits)
                    {
                        /*System.out.println("case 2 : temp array-------------");
                        for(int x=1;x>=0;x--)
                        {
                            for(int y=0;y<no_pits+1;y++)
                                System.out.print(temp[x][y]+" ");
                            System.out.println();
                        }*/
                        //int val;
                        int old_beta=beta;
                        //System.out.println("depth : "+d_depth);
                        if(depth%2==1)
                            val=alpha_beta(node, depth+1, alpha, beta, temp, next_player, true, depth, false, arr, cutoff, my_player, opposite_player,opt1);
                        else
                            val=alpha_beta(node, depth, alpha, beta, temp, next_player, true, old_depth, false, arr, cutoff, my_player, opposite_player,opt1);
                        //System.out.println("old_node : "+old_node+" value : "+value);
                        value=Math.min(val,value);
                        if(value<=alpha)
                        {
                            opt1.write(old_node+","+depth+","+print(value)+","+print(alpha)+","+print(beta));
                            opt1.newLine();
                            return value;
                        }
                        else
                        {    
                            beta=Math.min(value,beta);
                            opt1.write(old_node+","+depth+","+print(value)+","+print(alpha)+","+print(beta));
                            opt1.newLine();
                        }
                    }
                    else if(pointer_side[0]==no_pits)
                    {
                        /*System.out.println("case 3 : temp array-------------");
                        for(int x=1;x>=0;x--)
                        {
                            for(int y=0;y<no_pits+1;y++)
                                System.out.print(temp[x][y]+" ");
                            System.out.println();
                        }*/
                        //int val;
                        int old_beta=beta;
                        //System.out.println("old_beta : "+old_beta);
                        //System.out.println("depth : "+d_depth);
                        if(depth%2==1)
                            val=alpha_beta(node, depth+1, alpha, beta, temp, player, false, depth, true, arr, cutoff, my_player, opposite_player,opt1);
                        else
                            val=alpha_beta(node, depth, alpha, beta, temp, player, false, old_depth, true, arr, cutoff, my_player, opposite_player,opt1);
                        
                        //System.out.println("old_node : "+old_node+" value : "+value);
                        value=Math.min(val,value);
                        repeat_chance=true;
                        //System.out.println("new Beta : "+beta);
                        if(value<=alpha)
                        {
                            opt1.write(old_node+","+depth+","+print(value)+","+print(alpha)+","+print(beta));
                            opt1.newLine();
                            return value;
                        }
                        else
                        {
                            beta=Math.min(value,beta);
                            opt1.write(old_node+","+depth+","+print(value)+","+print(alpha)+","+print(beta));
                            opt1.newLine();
                        }
                    }
                }
            }
            return value;
        }
        
        return alpha;
    }
    public int[] indexOf_lastStone(int[][]temp,int player,int pit_side,int pointer,int pit)
    {
        int no_stones=temp[player-1][pit];
        temp[player-1][pit]=0;
        while(no_stones!=0)
        {
            no_stones--;
            if(player==1 && pit_side==1 && (pointer+1)<no_pits)
            {
                pointer++;
                temp[pit_side-1][pointer]+=1;
                //System.out.println(pointer);
            }
            else if(player==1 && pit_side==2 && (pointer-1)>=0)
            {
                pointer--;
                temp[pit_side-1][pointer]+=1;
            }
            else if(player==2 && pit_side==2 && (pointer-1>=0))
            {
                pointer--;
                temp[pit_side-1][pointer]+=1;
            }
            else if(player==2 && pit_side==1 && (pointer+1)<no_pits)
            {
                pointer++;
                temp[pit_side-1][pointer]+=1;
            }
            else if(player==1 && pit_side==1 && (pointer+1)==no_pits)
            {
                temp[player-1][no_pits]+=1;
                if(no_stones!=0)
                {
                    pointer=no_pits;
                    pit_side=2;
                }
                else
                    pointer=no_pits;
            }
            else if(player==1 && pit_side==2 && (pointer-1)==-1)
            {
                temp[player-1][0]+=1;
                pointer=0;
                pit_side=1;
            }
            else if(player==2 && pit_side==2 && (pointer-1)==-1)
            {
                temp[player-1][no_pits]+=1;
                if(no_stones!=0)
                {
                    pointer=-1;
                    pit_side=1;
                }
                else
                    pointer=no_pits;
            }
            else if(player==2 && pit_side==1 && (pointer+1)==no_pits)
            {
                temp[player-1][no_pits-1]+=1;
                pointer=no_pits-1;
                pit_side=2;   
            }
        }   
        int[]a={pointer,pit_side};
        return a;
    }
}

public class mancala {
    public static void main(String[]args)throws IOException
    {
        try
        {
            FileInputStream fstream=new FileInputStream(args[1]);
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            
            
            String str=br.readLine();
            
            switch(str)
            {
                case "1":
                {
                    int player=Integer.parseInt(br.readLine());
                    str=br.readLine();
                    
                    str=br.readLine();
                    String s[]=str.split("\\s+");
                    
                    int board[][]=new int[2][s.length+1];
                    
                    for(int i=0;i<s.length;i++)
                        board[1][i]=Integer.parseInt(s[i]);
                    
                    str=br.readLine();
                    String s1[]=str.split("\\s+");
                    for(int i=0;i<s1.length;i++)
                        board[0][i]=Integer.parseInt(s1[i]);
                      
                    
                    /*System.out.println("just ini-----------");
                    for(int i=s.length;i>=0;i--)
                        System.out.print(board[1][i]);
                    System.out.println();
                    for(int i=0;i<=s.length;i++)
                        System.out.print(board[0][i]);
                    System.out.println();*/
                    board[1][s.length]=Integer.parseInt(br.readLine());
                    board[0][s.length]=Integer.parseInt(br.readLine());
                    
                    int no_pits=s.length;
                    case4 c=new case4(player,1,board,no_pits);
                    c.test2();
                    
                }break;
                case "2":
                {
                    int player=Integer.parseInt(br.readLine());
                    int cutoff=Integer.parseInt(br.readLine());
                    str=br.readLine();
                    String s[]=str.split("\\s+");
                    
                    
                    int board[][]=new int[2][s.length+1];
                    for(int i=0;i<s.length;i++)
                        board[1][i]=Integer.parseInt(s[i]);
                       
                    //System.out.println("succ");
                    str=br.readLine();
                    String s1[]=str.split("\\s+");
                    for(int i=0;i<s1.length;i++)
                        board[0][i]=Integer.parseInt(s1[i]);
                    
                    board[1][s.length]=Integer.parseInt(br.readLine());
                    board[0][s.length]=Integer.parseInt(br.readLine());
                    
                    /*for(int i=0;i<2;i++)
                    {
                        for(int j=0;j<s.length+1;j++)
                            System.out.print(board[i][j]+" ");
                        System.out.println();
                    }*/
                    
                    int no_pits=s.length;
                    case4 c=new case4(player,cutoff,board,no_pits);
                    c.test();
                    
                }break;
                case "3":
                {
                    int player=Integer.parseInt(br.readLine());
                    int cutoff=Integer.parseInt(br.readLine());
                    str=br.readLine();
                    String s[]=str.split("\\s+");
                    
                    
                    int board[][]=new int[2][s.length+1];
                    for(int i=0;i<s.length;i++)
                        board[1][i]=Integer.parseInt(s[i]);
                       
                    //System.out.println("succ");
                    str=br.readLine();
                    String s1[]=str.split("\\s+");
                    for(int i=0;i<s1.length;i++)
                        board[0][i]=Integer.parseInt(s1[i]);
                    
                    board[1][s.length]=Integer.parseInt(br.readLine());
                    board[0][s.length]=Integer.parseInt(br.readLine());
                    
                    int no_pits=s.length;
                    case4 c=new case4(player,cutoff,board,no_pits);
                    c.test1();
                    
                }break;
                case "4":
                {
                    
                }break;
            }
        }
        catch(Exception e)
        {
            System.err.println("Error : "+e.getMessage());
        }
    }
    
}
