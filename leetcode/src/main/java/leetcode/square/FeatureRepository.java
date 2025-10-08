package leetcode.square;

import java.util.*;
import java.util.stream.Collectors;

public class FeatureRepository {

    Map<String, Set<Feature>> tagToFeatureMap = new HashMap<>();
    static class Feature {
        String name;
        Set<String> tags;

        public Feature(String feature, String... tags) {
            this.name = feature;
            this.tags = new HashSet<>(Arrays.asList(tags));
        }
    }

    public FeatureRepository() {
        addFeature(new Feature("feature1", "string", "categorical"));
        addFeature(new Feature("feature2", "numeric"));
        addFeature(new Feature("feature3", "numeric", "categorical"));
        addFeature(new Feature("feature4", "string", "splittable"));
        addFeature(new Feature("feature5", "string"));
    }

    public void addFeature(Feature feature) {
        for (String tag:feature.tags) {
            tagToFeatureMap.computeIfAbsent(tag, a -> new HashSet<>()).add(feature);
        }
    }
}

class FeatureFinder extends FeatureRepository {
    public Set<String> findFeaturesByTag(String tag) {
        return tagToFeatureMap.getOrDefault(tag, Collections.emptySet())
                .stream()
                .map(feature -> feature.name)
                .collect(Collectors.toSet());
    }

    public Set<String> findFeaturesByAnyTag(String... tags) {
        return Arrays.stream(tags)
                .flatMap(tag -> tagToFeatureMap.getOrDefault(tag, Collections.emptySet()).stream())
                .map(feature -> feature.name)
                .collect(Collectors.toSet());
    }

    public Set<String> findFeaturesByAllTags(String... tags) {
        return Arrays.stream(tags)
                .map(tag -> tagToFeatureMap.getOrDefault(tag, Collections.emptySet()))
                .reduce((set1, set2) -> set1.stream()
                        .filter(set2::contains)
                        .collect(Collectors.toSet()))
                .orElse(Collections.emptySet())
                .stream()
                .map(feature -> feature.name)
                .collect(Collectors.toSet());
    }

    public static void main(String[] args) {
        FeatureFinder finder = new FeatureFinder();
        System.out.println(finder.findFeaturesByTag("string"));
        System.out.println(finder.findFeaturesByAnyTag("categorical", "splittable"));
        System.out.println(finder.findFeaturesByAllTags("categorical", "string"));
    }
}