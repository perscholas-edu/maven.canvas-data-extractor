package com.github.perscholas.utils;

import org.apache.commons.text.similarity.JaroWinklerDistance;
import org.apache.commons.text.similarity.SimilarityScore;

import java.util.*;

/**
 * @author leonhunter
 * @created 02/02/2020 - 7:40 PM
 */
public class StringEvaluator {
    private final String baseString;
    private final SimilarityScore<Double> similarityScorer;

    public StringEvaluator(SimilarityScore<Double> similarityScorer, String baseString) {
        this.similarityScorer = similarityScorer;
        this.baseString = baseString;
    }

    public StringEvaluator(String baseString) {
        this(new JaroWinklerDistance(), baseString);
    }

    public String getMostSimilar(List<String> stringsToCompareAgainst) {
        Map<String, Double> similarityMap = getSimilarityMap(stringsToCompareAgainst);
        Collection<Double> values = similarityMap.values();
        List<Double> ascendingValues = new ArrayList<>(new TreeSet<>(values));
        Double highestSimilarity = ascendingValues.get(0);
        for (String key : similarityMap.keySet()) {
            Double value = similarityMap.get(key);
            if (value.equals(highestSimilarity)) {
                return key;
            }
        }
        return null;
    }

    public Map<String, Double> getSimilarityMap(List<String> stringsToCompareAgainst) {
        Map<String, Double> map = new HashMap<>();
        for (String stringToCompareAgainst : stringsToCompareAgainst) {
            map.put(stringToCompareAgainst, getSimilarity(stringToCompareAgainst));
        }
        return map;
    }

    public Double getSimilarity(String stringToCompareAgainst) {
        return similarityScorer.apply(baseString, stringToCompareAgainst);
    }
}
