package pl.edu.agh.JakDojadeProlog.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class Result implements Comparable<Result>{
    private String time;
    private List<String> busStops;
    private List<String> busLines;

    @Override
    public int compareTo(Result r) {
        return Integer.valueOf(this.getTime()).compareTo(Integer.valueOf(r.getTime()));
    }
}
