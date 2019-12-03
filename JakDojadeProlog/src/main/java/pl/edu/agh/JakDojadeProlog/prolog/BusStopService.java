package pl.edu.agh.JakDojadeProlog.prolog;

import org.jpl7.Atom;
import org.jpl7.Query;
import org.jpl7.Term;
import org.jpl7.Variable;
import org.springframework.stereotype.Component;
import pl.edu.agh.JakDojadeProlog.model.BusStop;
import pl.edu.agh.JakDojadeProlog.model.Result;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class BusStopService {

    public BusStopService() {
        Query q1 = new Query("consult", new Term[]{new Atom("busses.pl")});
        System.out.println("Prolog file loaded " + (q1.hasSolution() ? "succeeded" : "failed"));
    }

    public List<BusStop> getAllBusStops() {
        Variable BusStopName = new Variable("BusStopName");
        Variable Line = new Variable("Line");

        Query q =
                new Query(
                        "stop",
                        new Term[]{BusStopName, Line}
                );

        Map<String, Term>[] solutions = q.allSolutions();
        List<BusStop> result = new LinkedList<>();

        for (Map<String, Term> solution : solutions) {
            BusStop busStop = new BusStop(solution.get("BusStopName").name(),
                    Arrays.stream(solution.get("Line").toTermArray())
                            .filter(arg -> arg.atomType().equals("text"))
                            .map(Term::name)
                            .collect(Collectors.toList()));
            result.add(busStop);
        }
        return result;
    }

    public List<Result> getAllRoutes(String startBusStop, String endBusStop) {
        Variable Route = new Variable("Route");
        Variable Time = new Variable("Time");
        Variable ListOfLines = new Variable("ListOfLines");
        Query q =
                new Query(
                        "route",
                        new Term[]{new Atom(startBusStop), new Atom(endBusStop), Route, Time, ListOfLines}
                );
        Map<String, Term>[] solutions = q.allSolutions();
        List<Result> resultList = new LinkedList<>();

        for (Map<String, Term> solution : solutions) {
            Result result = new Result(solution.get("Time").bigValue().toString(),
                    Arrays.stream(solution.get("Route").toTermArray())
                            .filter(arg -> arg.atomType().equals("text"))
                            .map(Term::name)
                            .collect(Collectors.toList()),
                    Arrays.stream(solution.get("ListOfLines").toTermArray())
                            .filter(arg -> arg.atomType().equals("text"))
                            .map(Term::name)
                            .collect(Collectors.toList())
                    );
            resultList.add(result);
            System.out.println(result);
        }
        return resultList;
    }
}
