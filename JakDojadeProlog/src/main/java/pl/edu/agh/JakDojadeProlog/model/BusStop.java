package pl.edu.agh.JakDojadeProlog.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Setter
@Getter
@ToString
@AllArgsConstructor
public class BusStop {
    private String name;
    private List<String> availableLines;
}
