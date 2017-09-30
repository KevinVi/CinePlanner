package com.example.cinemaplanner;


import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.bayes.NaiveBayesMultinomial;
import weka.classifiers.bayes.NaiveBayesUpdateable;
import weka.classifiers.meta.AdditiveRegression;
import weka.classifiers.meta.MultiClassClassifier;
import weka.classifiers.rules.ZeroR;
import weka.core.*;
import weka.core.converters.ConverterUtils;

import java.util.ArrayList;
import java.util.List;

public class WekaTest {

    public static void main(String[] args) throws Exception {




        List<String> gender = new ArrayList<>();
        gender.add("28");
        gender.add("12");
        gender.add("19");
        gender.add("35");
        gender.add("80");
        gender.add("99");
        gender.add("16");
        gender.add("18");
        gender.add("10751");
        gender.add("14");
        gender.add("36");
        gender.add("27");
        gender.add("10402");
        gender.add("9648");
        gender.add("10749");
        gender.add("878");
        gender.add("10770");
        gender.add("53");
        gender.add("10752");
        gender.add("37");
        Attribute genre1 = new Attribute("genre1", gender);

        //declare
        Attribute genre2 = new Attribute("genre2", gender);


        //declare
        Attribute genre3 = new Attribute("genre3", gender);


        List<String> genreNumero1 = new ArrayList<>();
        genreNumero1.add("12");
        genreNumero1.add("28");
        genreNumero1.add("18");
        genreNumero1.add("28");
        genreNumero1.add("35");
        genreNumero1.add("878");
        genreNumero1.add("35");
        genreNumero1.add("99");
        genreNumero1.add("878");
        genreNumero1.add("18");
        genreNumero1.add("53");
        genreNumero1.add("18");
        genreNumero1.add("35");
        genreNumero1.add("18");
        genreNumero1.add("10749");
        genreNumero1.add("18");
        genreNumero1.add("99");
        genreNumero1.add("27");
        genreNumero1.add("12");
        genreNumero1.add("53");
        genreNumero1.add("28");
        genreNumero1.add("12");
        genreNumero1.add("9648");
        genreNumero1.add("36");
        genreNumero1.add("18");
        genreNumero1.add("28");
        genreNumero1.add("18");
        genreNumero1.add("28");
        genreNumero1.add("28");
        genreNumero1.add("28");
        genreNumero1.add("28");
        genreNumero1.add("12");
        genreNumero1.add("28");
        genreNumero1.add("28");
        genreNumero1.add("12");
        genreNumero1.add("35");
        genreNumero1.add("18");
        genreNumero1.add("18");
        genreNumero1.add("28");
        genreNumero1.add("28");
        genreNumero1.add("28");
        genreNumero1.add("28");
        genreNumero1.add("12");
        genreNumero1.add("12");
        genreNumero1.add("28");
        genreNumero1.add("18");
        genreNumero1.add("18");
        genreNumero1.add("12");
        genreNumero1.add("28");
        genreNumero1.add("878");
        genreNumero1.add("16");
        genreNumero1.add("10752");
        genreNumero1.add("12");
        genreNumero1.add("18");
        genreNumero1.add("12");
        genreNumero1.add("28");
        genreNumero1.add("18");
        genreNumero1.add("37");
        genreNumero1.add("16");
        genreNumero1.add("80");
        genreNumero1.add("80");
        genreNumero1.add("12");
        genreNumero1.add("18");
        genreNumero1.add("12");
        genreNumero1.add("28");
        genreNumero1.add("28");
        genreNumero1.add("28");
        genreNumero1.add("16");
        genreNumero1.add("16");
        genreNumero1.add("18");
        genreNumero1.add("878");
        genreNumero1.add("10751");
        genreNumero1.add("12");
        genreNumero1.add("12");
        genreNumero1.add("12");
        genreNumero1.add("16");
        genreNumero1.add("12");
        genreNumero1.add("35");
        genreNumero1.add("878");
        genreNumero1.add("53");
        genreNumero1.add("16");
        genreNumero1.add("878");
        genreNumero1.add("12");
        genreNumero1.add("28");
        genreNumero1.add("80");
        genreNumero1.add("12");
        genreNumero1.add("12");
        genreNumero1.add("53");
        genreNumero1.add("12");
        genreNumero1.add("28");
        genreNumero1.add("878");
        genreNumero1.add("12");
        genreNumero1.add("28");
        genreNumero1.add("28");
        genreNumero1.add("10751");
        genreNumero1.add("28");
        genreNumero1.add("16");
        genreNumero1.add("12");
        genreNumero1.add("14");


        List<String> genreNumero2 = new ArrayList<>();
        genreNumero2.add("18");
        genreNumero2.add("12");
        genreNumero2.add("27");
        genreNumero2.add("53");
        genreNumero2.add("18");
        genreNumero2.add("18");
        genreNumero2.add("28");
        genreNumero2.add("0");
        genreNumero2.add("0");
        genreNumero2.add("36");
        genreNumero2.add("0");
        genreNumero2.add("878");
        genreNumero2.add("0");
        genreNumero2.add("53");
        genreNumero2.add("0");
        genreNumero2.add("10770");
        genreNumero2.add("80");
        genreNumero2.add("28");
        genreNumero2.add("16");
        genreNumero2.add("80");
        genreNumero2.add("80");
        genreNumero2.add("14");
        genreNumero2.add("53");
        genreNumero2.add("18");
        genreNumero2.add("80");
        genreNumero2.add("9648");
        genreNumero2.add("37");
        genreNumero2.add("878");
        genreNumero2.add("878");
        genreNumero2.add("878");
        genreNumero2.add("18");
        genreNumero2.add("14");
        genreNumero2.add("12");
        genreNumero2.add("12");
        genreNumero2.add("14");
        genreNumero2.add("18");
        genreNumero2.add("0");
        genreNumero2.add("12");
        genreNumero2.add("80");
        genreNumero2.add("12");
        genreNumero2.add("12");
        genreNumero2.add("12");
        genreNumero2.add("14");
        genreNumero2.add("14");
        genreNumero2.add("12");
        genreNumero2.add("10749");
        genreNumero2.add("35");
        genreNumero2.add("28");
        genreNumero2.add("12");
        genreNumero2.add("28");
        genreNumero2.add("35");
        genreNumero2.add("18");
        genreNumero2.add("14");
        genreNumero2.add("35");
        genreNumero2.add("28");
        genreNumero2.add("12");
        genreNumero2.add("80");
        genreNumero2.add("18");
        genreNumero2.add("10751");
        genreNumero2.add("18");
        genreNumero2.add("35");
        genreNumero2.add("28");
        genreNumero2.add("28");
        genreNumero2.add("14");
        genreNumero2.add("12");
        genreNumero2.add("12");
        genreNumero2.add("12");
        genreNumero2.add("35");
        genreNumero2.add("10751");
        genreNumero2.add("53");
        genreNumero2.add("28");
        genreNumero2.add("14");
        genreNumero2.add("10751");
        genreNumero2.add("878");
        genreNumero2.add("14");
        genreNumero2.add("10751");
        genreNumero2.add("35");
        genreNumero2.add("0");
        genreNumero2.add("28");
        genreNumero2.add("18");
        genreNumero2.add("12");
        genreNumero2.add("53");
        genreNumero2.add("14");
        genreNumero2.add("12");
        genreNumero2.add("9648");
        genreNumero2.add("14");
        genreNumero2.add("14");
        genreNumero2.add("80");
        genreNumero2.add("14");
        genreNumero2.add("12");
        genreNumero2.add("12");
        genreNumero2.add("14");
        genreNumero2.add("18");
        genreNumero2.add("12");
        genreNumero2.add("16");
        genreNumero2.add("80");
        genreNumero2.add("35");
        genreNumero2.add("28");
        genreNumero2.add("28");


        List<String> genreNumero3 = new ArrayList<>();
        genreNumero3.add("27");
        genreNumero3.add("35");
        genreNumero3.add("0");
        genreNumero3.add("0");
        genreNumero3.add("0");
        genreNumero3.add("9648");
        genreNumero3.add("12");
        genreNumero3.add("0");
        genreNumero3.add("0");
        genreNumero3.add("53");
        genreNumero3.add("0");
        genreNumero3.add("0");
        genreNumero3.add("0");
        genreNumero3.add("0");
        genreNumero3.add("0");
        genreNumero3.add("0");
        genreNumero3.add("0");
        genreNumero3.add("0");
        genreNumero3.add("10751");
        genreNumero3.add("0");
        genreNumero3.add("18");
        genreNumero3.add("28");
        genreNumero3.add("18");
        genreNumero3.add("53");
        genreNumero3.add("0");
        genreNumero3.add("878");
        genreNumero3.add("0");
        genreNumero3.add("12");
        genreNumero3.add("0");
        genreNumero3.add("0");
        genreNumero3.add("878");
        genreNumero3.add("28");
        genreNumero3.add("878");
        genreNumero3.add("878");
        genreNumero3.add("28");
        genreNumero3.add("10749");
        genreNumero3.add("0");
        genreNumero3.add("878");
        genreNumero3.add("18");
        genreNumero3.add("53");
        genreNumero3.add("14");
        genreNumero3.add("14");
        genreNumero3.add("10751");
        genreNumero3.add("28");
        genreNumero3.add("35");
        genreNumero3.add("10749");
        genreNumero3.add("16");
        genreNumero3.add("878");
        genreNumero3.add("878");
        genreNumero3.add("18");
        genreNumero3.add("10751");
        genreNumero3.add("18");
        genreNumero3.add("28");
        genreNumero3.add("0");
        genreNumero3.add("878");
        genreNumero3.add("14");
        genreNumero3.add("0");
        genreNumero3.add("12");
        genreNumero3.add("10751");
        genreNumero3.add("35");
        genreNumero3.add("28");
        genreNumero3.add("878");
        genreNumero3.add("53");
        genreNumero3.add("10751");
        genreNumero3.add("14");
        genreNumero3.add("14");
        genreNumero3.add("14");
        genreNumero3.add("10751");
        genreNumero3.add("0");
        genreNumero3.add("9648");
        genreNumero3.add("53");
        genreNumero3.add("12");
        genreNumero3.add("14");
        genreNumero3.add("53");
        genreNumero3.add("10751");
        genreNumero3.add("0");
        genreNumero3.add("878");
        genreNumero3.add("0");
        genreNumero3.add("12");
        genreNumero3.add("878");
        genreNumero3.add("10751");
        genreNumero3.add("18");
        genreNumero3.add("10751");
        genreNumero3.add("80");
        genreNumero3.add("53");
        genreNumero3.add("10751");
        genreNumero3.add("10751");
        genreNumero3.add("0");
        genreNumero3.add("28");
        genreNumero3.add("80");
        genreNumero3.add("14");
        genreNumero3.add("10751");
        genreNumero3.add("27");
        genreNumero3.add("16");
        genreNumero3.add("16");
        genreNumero3.add("0");
        genreNumero3.add("10751");
        genreNumero3.add("14");
        genreNumero3.add("0");

        // Declare yes / no
        List<String> isGood = new ArrayList<>();
        isGood.add("yes");
        isGood.add("no");
        Attribute go = new Attribute("isGood", isGood);

        ArrayList<Attribute> atts = new ArrayList<Attribute>();
        atts.add(genre1);
        atts.add(genre2);
        atts.add(genre3);
        atts.add(go);


        List<String> result = new ArrayList<>();
        result.add("no");
        result.add("yes");
        result.add("no");
        result.add("no");
        result.add("no");
        result.add("no");
        result.add("no");
        result.add("no");
        result.add("yes");
        result.add("no");
        result.add("no");
        result.add("no");
        result.add("no");
        result.add("no");
        result.add("no");
        result.add("no");
        result.add("no");
        result.add("no");
        result.add("no");
        result.add("yes");
        result.add("yes");
        result.add("yes");
        result.add("no");
        result.add("yes");
        result.add("no");
        result.add("yes");
        result.add("yes");
        result.add("yes");
        result.add("yes");
        result.add("yes");
        result.add("yes");
        result.add("yes");
        result.add("yes");
        result.add("yes");
        result.add("yes");
        result.add("no");
        result.add("no");
        result.add("no");
        result.add("yes");
        result.add("yes");
        result.add("yes");
        result.add("yes");
        result.add("no");
        result.add("yes");
        result.add("yes");
        result.add("no");
        result.add("no");
        result.add("yes");
        result.add("yes");
        result.add("yes");
        result.add("no");
        result.add("no");
        result.add("yes");
        result.add("no");
        result.add("yes");
        result.add("yes");
        result.add("no");
        result.add("no");
        result.add("no");
        result.add("yes");
        result.add("yes");
        result.add("no");
        result.add("yes");
        result.add("no");
        result.add("yes");
        result.add("yes");
        result.add("yes");
        result.add("no");
        result.add("no");
        result.add("yes");
        result.add("yes");
        result.add("no");
        result.add("no");
        result.add("no");
        result.add("no");
        result.add("no");
        result.add("yes");
        result.add("no");
        result.add("yes");
        result.add("yes");
        result.add("no");
        result.add("no");
        result.add("no");
        result.add("yes");
        result.add("yes");
        result.add("no");
        result.add("no");
        result.add("yes");
        result.add("no");
        result.add("yes");
        result.add("no");
        result.add("no");
        result.add("yes");
        result.add("no");
        result.add("no");
        result.add("no");
        result.add("no");
        result.add("no");
        result.add("yes");


        //petit jeu de donné

        ArrayList<String> genreNumero1Bis = new ArrayList<>();
        genreNumero1Bis.add("53");
        genreNumero1Bis.add("16");
        genreNumero1Bis.add("878");
        genreNumero1Bis.add("12");
        genreNumero1Bis.add("80");
        genreNumero1Bis.add("28");
        genreNumero1Bis.add("18");
        genreNumero1Bis.add("12");
        genreNumero1Bis.add("28");
        genreNumero1Bis.add("35");
        genreNumero1Bis.add("9648");
        genreNumero1Bis.add("18");

        ArrayList<String> genreNumero2Bis = new ArrayList<>();
        genreNumero2Bis.add("18");
        genreNumero2Bis.add("12");
        genreNumero2Bis.add("53");
        genreNumero2Bis.add("18");
        genreNumero2Bis.add("18");
        genreNumero2Bis.add("18");
        genreNumero2Bis.add("53");
        genreNumero2Bis.add("28");
        genreNumero2Bis.add("12");
        genreNumero2Bis.add("18");
        genreNumero2Bis.add("53");
        genreNumero2Bis.add("35");
        ArrayList<String> genreNumero3Bis = new ArrayList<>();
        genreNumero3Bis.add("878");
        genreNumero3Bis.add("10751");
        genreNumero3Bis.add("18");
        genreNumero3Bis.add("27");
        genreNumero3Bis.add("35");
        genreNumero3Bis.add("27");
        genreNumero3Bis.add("9648");
        genreNumero3Bis.add("878");
        genreNumero3Bis.add("878");
        genreNumero3Bis.add("10749");
        genreNumero3Bis.add("18");
        genreNumero3Bis.add("10749");

        ArrayList<String> resultBis = new ArrayList<>();
        resultBis.add("yes");
        resultBis.add("no");
        resultBis.add("no");
        resultBis.add("no");
        resultBis.add("yes");
        resultBis.add("yes");
        resultBis.add("yes");
        resultBis.add("yes");
        resultBis.add("yes");
        resultBis.add("no");
        resultBis.add("no");
        resultBis.add("no");
        Instances isTrainingSet = new Instances("training", atts, 10);
        isTrainingSet.setClassIndex(3);


//        for (int i = 0; i < result.size(); i++) {
//            Instance iExample = new DenseInstance(atts.size());
//            System.out.println(genreNumero1.get(i));
//            iExample.setValue(atts.get(0), genreNumero1.get(i));
//            if (genreNumero2.get(i).equals("0")) {
//                iExample.setValue(atts.get(1), Utils.missingValue());
//            } else {
//                iExample.setValue(atts.get(1), genreNumero2.get(i));
//            }
//            if (genreNumero3.get(i).equals("0")) {
//                iExample.setValue(atts.get(2), Utils.missingValue());
//            } else {
//                iExample.setValue(atts.get(2), genreNumero3.get(i));
//            }
//            iExample.setValue(atts.get(3), result.get(i));
//            isTrainingSet.add(iExample);
//        }
        for (int i = 0; i < resultBis.size(); i++) {
            Instance iExample = new DenseInstance(atts.size());
            System.out.println(genreNumero1.get(i));
            iExample.setValue(atts.get(0), genreNumero1Bis.get(i));
            if (genreNumero2Bis.get(i).equals("0")) {
                iExample.setValue(atts.get(1), Utils.missingValue());
            } else {
                iExample.setValue(atts.get(1), genreNumero2Bis.get(i));
            }
            if (genreNumero3Bis.get(i).equals("0")) {
                iExample.setValue(atts.get(2), Utils.missingValue());
            } else {
                iExample.setValue(atts.get(2), genreNumero3Bis.get(i));
            }
            iExample.setValue(atts.get(3), resultBis.get(i));
            isTrainingSet.add(iExample);
        }

        List<String> genreNew1 = new ArrayList<>();
        genreNew1.add("878");
        genreNew1.add("12");
        genreNew1.add("28");
        genreNew1.add("27");
        genreNew1.add("18");
        genreNew1.add("28");
        genreNew1.add("28");
        genreNew1.add("28");
        genreNew1.add("28");
        genreNew1.add("27");
        genreNew1.add("28");
        genreNew1.add("36");
        genreNew1.add("35");
        genreNew1.add("18");
        genreNew1.add("27");
        genreNew1.add("27");
        genreNew1.add("28");
        genreNew1.add("27");
        genreNew1.add("9648");
        genreNew1.add("99");


        List<String> genreNew2 = new ArrayList<>();
        genreNew2.add("0");
        genreNew2.add("16");
        genreNew2.add("12");
        genreNew2.add("53");
        genreNew2.add("0");
        genreNew2.add("16");
        genreNew2.add("12");
        genreNew2.add("878");
        genreNew2.add("80");
        genreNew2.add("9648");
        genreNew2.add("12");
        genreNew2.add("0");
        genreNew2.add("0");
        genreNew2.add("0");
        genreNew2.add("35");
        genreNew2.add("0");
        genreNew2.add("12");
        genreNew2.add("53");
        genreNew2.add("80");
        genreNew2.add("0");


        List<String> genreNew3 = new ArrayList<>();
        genreNew3.add("0");
        genreNew3.add("10751");
        genreNew3.add("14");
        genreNew3.add("0");
        genreNew3.add("0");
        genreNew3.add("878");
        genreNew3.add("878");
        genreNew3.add("0");
        genreNew3.add("53");
        genreNew3.add("53");
        genreNew3.add("14");
        genreNew3.add("0");
        genreNew3.add("0");
        genreNew3.add("0");
        genreNew3.add("0");
        genreNew3.add("0");
        genreNew3.add("14");
        genreNew3.add("0");
        genreNew3.add("53");
        genreNew3.add("0");


        Instances testSet = new Instances("test", atts, 10);

        for (int i = 0; i < genreNew1.size(); i++) {
            Instance iExample = new DenseInstance(atts.size());
            iExample.setValue(atts.get(0), genreNew1.get(i));
            if (genreNew2.get(i).equals("0")) {
                iExample.setValue(atts.get(1), Utils.missingValue());
            } else {
                iExample.setValue(atts.get(1), genreNew2.get(i));
            }
            if (genreNew3.get(i).equals("0")) {
                iExample.setValue(atts.get(2), Utils.missingValue());
            } else {
                iExample.setValue(atts.get(2), genreNew3.get(i));
            }
            iExample.setValue(atts.get(3), Utils.missingValue());
            testSet.add(iExample);
        }

        testSet.setClassIndex(testSet.numAttributes() - 1);

//        NaiveBayesUpdateable classifier = new NaiveBayesUpdateable();
//        classifier.buildClassifier(isTrainingSet);
        NaiveBayes classifier = new NaiveBayes();
        classifier.buildClassifier(isTrainingSet);


        System.out.println("");
        System.out.println("");
        System.out.println("");
        for (int i = 0; i < testSet.size(); i++) {

            double label = classifier.classifyInstance(testSet.instance(i));
            double[] predictionDistribution = classifier.distributionForInstance(testSet.instance(i));
            testSet.instance(i).setClassValue(label);
            System.out.println(testSet.get(i).toString(0)
                    + " : " + testSet.get(i).toString(1)
                    + " : " + testSet.get(i).toString(2)
                    + " : " + testSet.instance(i).toString(3)
                    + " : " + Double.toString(predictionDistribution[0]));
        }

    }


}