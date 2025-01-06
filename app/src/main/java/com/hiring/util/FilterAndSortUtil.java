package com.hiring.util;

import com.hiring.model.CandidateModel;

import java.util.List;
import java.util.stream.Collectors;

public class FilterAndSortUtil {
    public static List<CandidateModel> processItems(List<CandidateModel> items) {
        return items.stream()
                .filter(item -> item.getName() != null && !item.getName().isEmpty())
                .sorted((o1, o2) -> {
                    if (o1.getListId() != o2.getListId()) {
                        return Integer.compare(o1.getListId(), o2.getListId());
                    }
                    return o1.getName().compareTo(o2.getName());
                })
                .collect(Collectors.toList());
    }
}
