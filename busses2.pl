%busA
    stop(a, [busA]).            
    stop(b, [busA]).            
    stop(c, [busA]).            
    stop(d, [busA,busB]).       
    stop(e, [busA,busC]).       
%busB
    stop(f, [busB]).                        
    stop(g, [busB]).                    
    stop(h, [busB]).                    
    stop(i, [busB]).                    
    stop(j, [busB,busE]).       
%busC
    stop(k, [busC]).            
    stop(l, [busC]).            
    stop(n, [busC]).            
%busD
    stop(o, [busD]).            
    stop(p, [busD, busE]).      
    stop(r, [busD]).            
    stop(s, [busD]).            
    stop(t, [busD,busB,busC]). 
%busE
    stop(u, [busE]).            
    stop(v, [busE]).            
    stop(w, [busE,busC]).       
        
        
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
                      
    adjacent_stops(c,b,1).
    adjacent_stops(b,e,2).
    adjacent_stops(e,d,1).
    adjacent_stops(d,a,3).

%northern
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

    
findAllStops(Line, ListOfStops):-
        findall(Stop,(stop(Stop,NewLine), member(Line, NewLine)), ListOfStops).

        
route(Stop1, Stop2, Route, Time, ListOfLines):-                              
                route1( Stop1, Stop2, [], RouteReturn, 0, TimeReturn, [], ListOfLinesReturn),
                reverse([Stop2|RouteReturn], Route),
                stop(Stop2, L),
                reverse([L|ListOfLinesReturn], ListOfLines),
                %reverse(TimeReturn, Time).
                Time is TimeReturn.
                
route1(Stop1, Stop2, TempRoute, Route, TempTime, Time, TempLines, ListOfLines):-                    
        adjacent(Stop1, Stop2, TempTime1),
        \+member(Stop1, TempRoute),
        Route = [Stop1|TempRoute],
        stop(Stop1, L),
        ListOfLines = [L|TempLines],
        Time is TempTime1 + TempTime.

route1(Stop1, Stop2, TempRoute, Route, TempTime, Time, TempLines, ListOfLines):-                    
        adjacent(Stop1, Next, TempTime1),
        Next \== Stop2,
        \+member(Stop1, TempRoute),
        TempTime2 is TempTime1 + TempTime,
        stop(Stop1, L),
        route1(Next, Stop2, [Stop1|TempRoute], Route, TempTime2, Time, [L|TempLines], ListOfLines).
