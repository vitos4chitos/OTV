package main.helper;

import main.http.PictureResponse;

import java.util.Comparator;

public class MaxCountComparator implements Comparator<PictureResponse> {

    @Override
    public int compare(PictureResponse p1, PictureResponse p2){
        return (int) (p2.getCount() - p1.getCount());
    }
}
