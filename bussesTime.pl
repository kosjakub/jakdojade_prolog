%busA
    stop(a, [busA, busF]).            
    stop(b, [busA]).            
    stop(c, [busA,busF]).            
    stop(d, [busA,busB]).       
    stop(e, [busA,busC]).       
%busB
    stop(f, [busB]).                        
    stop(g, [busB]).                    
    stop(h, [busB]).                    
    stop(i, [busB]).                    
    stop(j, [busB,busE,busF]).       
%busC
    stop(k, [busC]).            
    stop(l, [busC]).            
    stop(n, [busC]).            
%busD
    stop(o, [busD]).            
    stop(p, [busD, busE]).      
    stop(r, [busD]).            
    stop(s, [busD, busF]).            
    stop(t, [busD,busB,busC,busF]). 
%busE
    stop(u, [busE]).            
    stop(v, [busE]).            
    stop(w, [busE,busC]). 
%busF
    stop(x, [busF]).  
    stop(y, [busF]).
    stop(z, [busF,busE]).

        
        
%-------------------------------------------------------------------------------------------------------------------------------

        adjacent(X,Y,Time):- adjacent_stops(X,Y,Time).
        adjacent(X,Y,Time):- adjacent_stops(Y,X,Time).

        
%busD_line
    adjacent_stops(s,r,1).  
    adjacent_stops(r,t,2).
    adjacent_stops(t,p,1).
    adjacent_stops(p,o,3).

%busB_line
    adjacent_stops(i,h,1).
    adjacent_stops(h,t,2).
    adjacent_stops(t,j,1).
    adjacent_stops(j,g,3).
    adjacent_stops(g,d,1).
    adjacent_stops(d,f,2).
           
%busA_line           
    adjacent_stops(c,b,1).
    adjacent_stops(b,e,2).
    adjacent_stops(e,d,1).
    adjacent_stops(d,a,3).

%busE_line
    adjacent_stops(u,w,1).
    adjacent_stops(w,j,2).
    adjacent_stops(j,p,1).
    adjacent_stops(p,v,3).

%busC_line
    adjacent_stops(k,n,1).
    adjacent_stops(n,t,2).
    adjacent_stops(t,w,1).
    adjacent_stops(w,e,3).
    adjacent_stops(e,l,4).

%busF_line
    adjacent_stops(a,x,3).
    adjacent_stops(x,j,3).
    adjacent_stops(y,t,2).
    adjacent_stops(y,s,1).
    adjacent_stops(s,z,4).
    adjacent_stops(c,z,2).
    adjacent_stops(z,u,1).


    
findAllStops(Line, ListOfStops):-
        findall(Stop,(stop(Stop,NewLine), member(Line, NewLine)), ListOfStops).

merge_adjacent([],[]).
merge_adjacent([_],[]) :- !.
merge_adjacent([X,Y|ZS], [M|MS]) :- intersection(X,Y,M), merge_adjacent([Y|ZS], MS).

merge_list(A, L):-
        merge_adjacent(A, L).

merge_adjacent_flatten([], []).
merge_adjacent_flatten([_], []) :- !.
merge_adjacent_flatten([X,Y|ZS], [M|MS]) :- 
    flatten(X, X1), 
    flatten(Y, Y1), 
    intersection(X1,Y1,M), 
    merge_adjacent_flatten([Y|ZS], MS).

occurrences_of(List, X, Count) :- aggregate_all(count, member(X, List), Count).
        
route(Stop1, Stop2, Route, Time, ListOfLines):-                              
                route1( Stop1, Stop2, [], RouteReturn, 0, TimeReturn, [], ListOfLinesReturn),
                reverse([Stop2|RouteReturn], Route),
                reverse(ListOfLinesReturn, ListOfLines),
                merge_adjacent_flatten(ListOfLines, Merged),
                occurrences_of(Merged, [], Penalties),
                Time is TimeReturn + Penalties*3.
                
route1(Stop1, Stop2, TempRoute, Route, TempTime, Time, TempLines, ListOfLines):-                    
        adjacent(Stop1, Stop2, TempTime1),
        \+member(Stop1, TempRoute),
        Route = [Stop1|TempRoute],
        stop(Stop1, L1),
        stop(Stop2, L2),
        merge_list([L1, L2], L),
        ListOfLines = [L|TempLines],
        Time is TempTime1 + TempTime.

route1(Stop1, Stop2, TempRoute, Route, TempTime, Time, TempLines, ListOfLines):-                    
        adjacent(Stop1, Next, TempTime1),
        Next \== Stop2,
        \+member(Stop1, TempRoute),
        TempTime2 is TempTime1 + TempTime,
        stop(Stop1, L1),
        stop(Next, L2),
        merge_list([L1, L2], L),
        route1(Next, Stop2, [Stop1|TempRoute], Route, TempTime2, Time, [L|TempLines], ListOfLines).
