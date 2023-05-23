package com.company.job.myhasjobwithjwt.controller;

import com.company.job.myhasjobwithjwt.domains.Ads;
import com.company.job.myhasjobwithjwt.domains.JobType;
import org.hibernate.mapping.Collection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Set;

public class TestMain {
    public static void main(String[] args) {

        ArrayList arrayList = new ArrayList<>();
        arrayList.add("44");
        arrayList.add("45");
        arrayList.add("96");
        arrayList.add(46);
        arrayList.add(new Ads());
        arrayList.add(new JobType(12, "12", true));

        arrayList.forEach(System.out::println);

        LinkedList linkedList = new LinkedList();

//        Set

    }
}
