package com.example.cinemaplanner;


import weka.classifiers.bayes.NaiveBayes;
import weka.core.*;
import weka.core.converters.ConverterUtils;

import java.util.ArrayList;
import java.util.List;

public class WekaTest {

    public static void main(String[] args) throws Exception {

        ConverterUtils.DataSource source1 = new ConverterUtils.DataSource("G:\\weather.arff");
        Instances train = source1.getDataSet();


//        @attribute outlook {sunny, overcast, rainy}
//        @attribute temperature numeric
//        @attribute humidity numeric
//        @attribute windy {TRUE, FALSE}
//        @attribute play {yes, no}


        // setting class attribute if the data format does not provide this information
        // For example, the XRFF format saves the class attribute information as well
        if (train.classIndex() == -1) {
            train.setClassIndex(train.numAttributes() - 1);
            System.out.println("Remove one" + train.classIndex());
        }

        ConverterUtils.DataSource source2 = new ConverterUtils.DataSource("G:\\test.arff");
        Instances test = source2.getDataSet();
        // setting class attribute if the data format does not provide this information
        // For example, the XRFF format saves the class attribute information as well
        if (test.classIndex() == -1) {
            System.out.println(" COUCOU " + test.classIndex());
            test.setClassIndex(train.numAttributes() - 1);
            System.out.println(test.classIndex());
        }

        // model

        NaiveBayes naiveBayes = new NaiveBayes();
        naiveBayes.buildClassifier(train);


        // this does the trick
        System.out.println("size >" + test.size());
        for (int i = 0; i < test.size(); i++) {
            double label = naiveBayes.classifyInstance(test.instance(i));
            double[] predictionDistribution = naiveBayes.distributionForInstance(test.instance(i));
            test.instance(i).setClassValue(label);
            System.out.println(source2.getDataSet().get(i).toString(0)
                    + " : " + source2.getDataSet().get(i).toString(1)
                    + " : " + source2.getDataSet().get(i).toString(2)
                    + " : " + source2.getDataSet().get(i).toString(3)
                    + " : " + test.instance(i).toString(4)
                    + " : " + Double.toString(predictionDistribution[0]));
        }


        // Declare outlook
        List<String> outlook = new ArrayList<>();
        outlook.add("sunny");
        outlook.add("overcast");
        outlook.add("rainy");
        Attribute Attribute1 = new Attribute("outlook", outlook);


        //declare temperature
        Attribute Attribute2 = new Attribute("temprature");

        //declare humidity
        Attribute Attribute3 = new Attribute("humidity");

        // Declare boolean 0 / 1
        //declare humidity
        Attribute Attribute4 = new Attribute("windy");

        // Declare yes / no
        List<String> play = new ArrayList<>();
        play.add("yes");
        play.add("no");
        Attribute Attribute5 = new Attribute("play", play);


        ArrayList<Attribute> atts = new ArrayList<Attribute>();
        atts.add(Attribute1);
        atts.add(Attribute2);
        atts.add(Attribute3);
        atts.add(Attribute4);
        atts.add(Attribute5);


        List<String> outlookValue = new ArrayList<>();
        outlookValue.add("sunny");
        outlookValue.add("sunny");
        outlookValue.add("overcast");
        outlookValue.add("rainy");
        outlookValue.add("rainy");
        outlookValue.add("rainy");
        outlookValue.add("overcast");
        outlookValue.add("overcast");
        outlookValue.add("sunny");
        outlookValue.add("sunny");
        outlookValue.add("rainy");
        outlookValue.add("sunny");
        outlookValue.add("overcast");
        outlookValue.add("overcast");
        outlookValue.add("rainy");

        List<Integer> temperatureValue = new ArrayList<>();
        temperatureValue.add(85);
        temperatureValue.add(80);
        temperatureValue.add(83);
        temperatureValue.add(70);
        temperatureValue.add(68);
        temperatureValue.add(65);
        temperatureValue.add(64);
        temperatureValue.add(64);
        temperatureValue.add(72);
        temperatureValue.add(69);
        temperatureValue.add(75);
        temperatureValue.add(75);
        temperatureValue.add(72);
        temperatureValue.add(81);
        temperatureValue.add(71);

        List<Integer> humidityValue = new ArrayList<>();
        humidityValue.add(85);
        humidityValue.add(90);
        humidityValue.add(86);
        humidityValue.add(96);
        humidityValue.add(80);
        humidityValue.add(70);
        humidityValue.add(65);
        humidityValue.add(60);
        humidityValue.add(95);
        humidityValue.add(70);
        humidityValue.add(80);
        humidityValue.add(70);
        humidityValue.add(90);
        humidityValue.add(75);
        humidityValue.add(91);


        int FALSE = 0;
        int TRUE = 0;
        List<Integer> windyValue = new ArrayList<>();
        windyValue.add(FALSE);
        windyValue.add(TRUE);
        windyValue.add(FALSE);
        windyValue.add(FALSE);
        windyValue.add(FALSE);
        windyValue.add(TRUE);
        windyValue.add(TRUE);
        windyValue.add(TRUE);
        windyValue.add(FALSE);
        windyValue.add(FALSE);
        windyValue.add(FALSE);
        windyValue.add(TRUE);
        windyValue.add(TRUE);
        windyValue.add(FALSE);
        windyValue.add(TRUE);

        List<String> result = new ArrayList<>();
        result.add("no");
        result.add("no");
        result.add("yes");
        result.add("yes");
        result.add("yes");
        result.add("no");
        result.add("yes");
        result.add("yes");
        result.add("no");
        result.add("yes");
        result.add("yes");
        result.add("yes");
        result.add("yes");
        result.add("yes");
        result.add("no");


        Instances isTrainingSet = new Instances("Rel", atts, 10);
        isTrainingSet.setClassIndex(4);
//        System.out.println("outlookvalue ->" + outlookValue);
//        System.out.println("temperatureValue ->" + temperatureValue);
//        System.out.println("humidityValue ->" + humidityValue);
//        System.out.println("windyValue ->" + windyValue);
//        System.out.println("result ->" + result);
        for (int i = 0; i < result.size(); i++) {
            Instance iExample = new DenseInstance(5);
            iExample.setValue(atts.get(0), outlookValue.get(i));
            iExample.setValue(atts.get(1), temperatureValue.get(i));
            iExample.setValue(atts.get(2), humidityValue.get(i));
            iExample.setValue(atts.get(3), windyValue.get(i));
            iExample.setValue(atts.get(4), result.get(i));
            isTrainingSet.add(iExample);
        }

        Instances testSet = new Instances("test", atts, 10);
        isTrainingSet.setClassIndex(4);
        for (int i = 0; i < result.size(); i++) {
            Instance iExample = new DenseInstance(5);
            iExample.setValue(atts.get(0), outlookValue.get(i));
            iExample.setValue(atts.get(1), temperatureValue.get(i));
            iExample.setValue(atts.get(2), humidityValue.get(i));
            iExample.setValue(atts.get(3), windyValue.get(i));
            iExample.setValue(atts.get(4), Utils.missingValue());
            testSet.add(iExample);
        }

        testSet.setClassIndex(testSet.numAttributes() - 1);


        NaiveBayes naiveBayesbis = new NaiveBayes();
        naiveBayesbis.buildClassifier(isTrainingSet);

        System.out.println("");
        System.out.println("");
        System.out.println("");
        for (int i = 0; i < testSet.size(); i++) {

            double label = naiveBayesbis.classifyInstance(testSet.instance(i));
            double[] predictionDistribution = naiveBayesbis.distributionForInstance(testSet.instance(i));
            testSet.instance(i).setClassValue(label);
            System.out.println(testSet.get(i).toString(0)
                    + " : " + testSet.get(i).toString(1)
                    + " : " + testSet.get(i).toString(2)
                    + " : " + testSet.get(i).toString(3)
                    + " : " + testSet.instance(i).toString(4)
                    + " : " + Double.toString(predictionDistribution[0]));
        }

    }


}