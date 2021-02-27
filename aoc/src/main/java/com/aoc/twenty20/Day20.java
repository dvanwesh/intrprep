package com.aoc.twenty20;

import com.aoc.common.Challenge;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day20 extends Challenge {
    Map<Long, Image> imageMap = new HashMap<>();
    protected Day20() {
        super(20, 2020);
        String[] inp = getResourceAsString().split("\n\n");
        Arrays.stream(inp).forEach(str->{
            String[] lines = str.split("\n");
            long title = Long.parseLong(lines[0].replace("Tile ","").replace(":",""));
            imageMap.put(title, new Image(lines));
        });
    }

    public static void main(String[] args) {
        new Day20().executeTasks();
    }

    @Override
    protected Object task1() {
        int n = (int) Math.sqrt(imageMap.size());
        List<Image> corners = imageMap.values().stream().filter(img ->
            imageMap.values().stream().filter(i -> i.getTitle() != img.getTitle()).filter(i -> i.commonEdges(img) == 1).count() == 2).collect(Collectors.toList());
        List<Image> centerBlocks = imageMap.values().stream().filter(img ->
            imageMap.values().stream().filter(i -> i.getTitle() != img.getTitle()).filter(i -> i.commonEdges(img) == 1).count() == 4).collect(Collectors.toList());
        //rearrange(corners);
        rearrangeFromCenter(centerBlocks);
        return corners.stream().mapToLong(Image::getTitle).reduce((a,b)->a*b);
    }

    private void rearrangeFromCenter(List<Image> centerBlocks) {
        //Long.parseLong()
        centerBlocks.stream().forEach(ctrImg->{
            Set<Image> neighbours = ctrImg.getAdjImages();
            neighbours.stream().forEach(nei->{
                if(nei.getEdges().stream().filter(e->ctrImg.getEdges().contains(e)).findAny().isPresent()){
                    if(nei.getTop().equals(ctrImg.getTop())){

                    }
                } else{

                }
            });
        });
    }

    private void rearrange(List<Image> corners) {
        corners.stream().forEach(corner -> {
            Set<Image> neighbours = corner.getAdjImages();
            neighbours.stream().forEach(nei->{
                if(nei.getEdges().stream().filter(edg->edg.equals(corner.getTop())).findAny().isPresent()){
                    if(nei.getTop().equals(corner.getTop())){
                        corner.rotateClockwise();
                        nei.rotateClockwise();
                        nei.sideFlip();
                    }
                }
            });
        });
    }

    @Override
    protected Object task2() {
        return null;
    }

    @Data
    class Image{
        private char[][] grid;
        private final long title;
        private String top;
        private String left;
        private String bottom;
        private String right;
        private List<String> edges;
        private long pos_x;
        private long pos_y;
        private Set<Image> adjImages;

        public Image(String[] lines) {
            title = Long.parseLong(lines[0].replace("Tile ","").replace(":",""));
            grid = IntStream.range(1, lines.length).mapToObj(i->lines[i].toCharArray()).toArray(char[][]::new);
            adjImages = new HashSet<>();
            markBoundaries();
        }

        private void markBoundaries() {
            top = String.valueOf(grid[0]);
            right = StringUtils.join(Arrays.stream(grid).map(r->r[r.length-1]).toArray());
            bottom = String.valueOf(grid[grid.length-1]);
            left = StringUtils.join(Arrays.stream(grid).map(r->r[0]).toArray());
            edges = new ArrayList<>();
            edges.add(top);
            edges.add(right);
            edges.add(bottom);
            edges.add(left);
        }

        public int commonEdges(Image other){
            int c = 4, n = (int) edges.stream().filter(e->other.edges.contains(e)).count();
            if(n==0){
                List<String> revEdges = edges.stream().map(e -> new StringBuilder(e).reverse().toString()).collect(Collectors.toList());
                n = (int) revEdges.stream().filter(r->other.edges.contains(r)).count();
            }
            if(n > 0){
                adjImages.add(other);
                other.getAdjImages().add(this);
                if(top.equals(other.bottom) || bottom.equals(other.top) || left.equals(other.right) || right.equals(other.left)){
                    return n;
                }
            }
            return n;
        }
        public void rotateClockwise(){
            int n = grid.length;
            for(int i = 0; i < n/2 ; i++){
                for(int j = i; j < n-1-i; j++){
                    char top = grid[i][j];
                    char right = grid[j][n-1-i];
                    char bottom = grid[n-1-i][n-1-j];
                    char left = grid[n-1-j][i];
                    grid[i][j] = left;
                    grid[j][n-1-i] = top;
                    grid[n-1-i][n-1-j] = right;
                    grid[n-1-j][i] = bottom;
                }
            }
            markBoundaries();
        }

        public void sideFlip(){
            Arrays.stream(grid).forEach(row->{
                int lo = 0, hi = row.length-1;
                while (lo < hi){
                    char tmp = row[lo];
                    row[lo] = row[hi];
                    row[hi] = tmp;
                    lo++;
                    hi--;
                }
            });
            markBoundaries();
        }

        public void topFlip(){
            int lo = 0, hi = grid.length-1;
            while (lo < hi){
                char[] tmp = grid[lo].clone();
                grid[lo] = grid[hi];
                grid[hi] = tmp;
                lo++;
                hi--;
            }
            markBoundaries();
        }

        public Set<Image> getAdjImages() {
            return adjImages.stream().sorted((img1, img2)->img2.getAdjImages().size()-img1.getAdjImages().size()).collect(Collectors.toCollection(LinkedHashSet::new));
        }
    }
}
