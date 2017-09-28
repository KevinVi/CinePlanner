package com.example.cinemaplanner;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.evaluation.NominalPrediction;
import weka.classifiers.rules.DecisionTable;
import weka.classifiers.rules.OneR;
import weka.classifiers.rules.PART;
import weka.classifiers.trees.DecisionStump;
import weka.classifiers.trees.J48;
import weka.core.*;
import weka.core.converters.ConverterUtils;

public class WekaTest {
    //    public static BufferedReader readDataFile(String filename) {
//        BufferedReader inputReader = null;
//
//        try {
//            FileReader fileReader = new FileReader(filename);
//            inputReader = new BufferedReader(fileReader);
//        } catch (FileNotFoundException ex) {
//            System.err.println("File not found: " + filename);
//        }
//
//        return inputReader;
//    }
//
//    public static Evaluation simpleClassify(Classifier model, Instances trainingSet, Instances testingSet) throws Exception {
//        Evaluation validation = new Evaluation(trainingSet);
//
//        model.buildClassifier(trainingSet);
//        validation.evaluateModel(model, testingSet);
//
//        return validation;
//    }
//
//    public static double calculateAccuracy(FastVector predictions) {
//        double correct = 0;
//
//        for (int i = 0; i < predictions.size(); i++) {
//            NominalPrediction np = (NominalPrediction) predictions.elementAt(i);
//            if (np.predicted() == np.actual()) {
//                correct++;
//            }
//        }
//
//        return 100 * correct / predictions.size();
//    }
//
//    public static Instances[][] crossValidationSplit(Instances data, int numberOfFolds) {
//        Instances[][] split = new Instances[2][numberOfFolds];
//
//        for (int i = 0; i < numberOfFolds; i++) {
//            split[0][i] = data.trainCV(numberOfFolds, i);
//            split[1][i] = data.testCV(numberOfFolds, i);
//        }
//
//        return split;
//    }
//
//    public static void main(String[] args) throws Exception {
//        // I've commented the code as best I can, at the moment.
//        // Comments are denoted by "//" at the beginning of the line.
//
//        BufferedReader datafile = readDataFile("G:\\iris.arff");
//
//        Instances data = new Instances(datafile);
//        data.setClassIndex(data.numAttributes() - 1);
//
//        // Choose a type of validation split
//        Instances[][] split = crossValidationSplit(data, 10);
//
//        // Separate split into training and testing arrays
//        Instances[] trainingSplits = split[0];
//        Instances[] testingSplits  = split[1];
//
//        // Choose a set of classifiers
//        Classifier[] models = {     new J48(),
//                new PART(),
//                new DecisionTable(),
//                new OneR(),
//                new DecisionStump() };
//
//        // Run for each classifier model
//        for(int j = 0; j < models.length; j++) {
//
//            // Collect every group of predictions for current model in a FastVector
//            FastVector predictions = new FastVector();
//
//            // For each training-testing split pair, train and test the classifier
//            for(int i = 0; i < trainingSplits.length; i++) {
//                Evaluation validation = simpleClassify(models[j], trainingSplits[i], testingSplits[i]);
//                predictions.appendElements(validation.predictions());
//
//                // Uncomment to see the summary for each training-testing pair.
//                // System.out.println(models[j].toString());
//            }
//
//            // Calculate overall accuracy of current classifier on all splits
//            double accuracy = calculateAccuracy(predictions);
//
//            // Print current classifier's name and accuracy in a complicated, but nice-looking way.
//            System.out.println(models[j].getClass().getSimpleName() + ": " + String.format("%.2f%%", accuracy) + "\n=====================");
//        }
//
//    }
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
            System.out.println("Remove one");
            train.setClassIndex(train.numAttributes() - 1);
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
        humidityValue.add(65);
        humidityValue.add(95);
        humidityValue.add(70);
        humidityValue.add(80);
        humidityValue.add(70);
        humidityValue.add(90);
        humidityValue.add(75);
        humidityValue.add(91);

        List<Integer> windyValue = new ArrayList<>();
        windyValue.add(0);
        windyValue.add(1);
        windyValue.add(0);
        windyValue.add(0);
        windyValue.add(0);
        windyValue.add(1);
        windyValue.add(1);
        windyValue.add(1);
        windyValue.add(0);
        windyValue.add(0);
        windyValue.add(0);
        windyValue.add(1);
        windyValue.add(1);
        windyValue.add(0);
        windyValue.add(1);

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
        System.out.println("outlookvalue ->" + outlookValue);
        System.out.println("temperatureValue ->" + temperatureValue);
        System.out.println("humidityValue ->" + humidityValue);
        System.out.println("windyValue ->" + windyValue);
        System.out.println("result ->" + result);
        for (int i = 0; i < result.size(); i++) {
            Instance iExample = new DenseInstance(5);
            iExample.setValue(atts.get(0), outlookValue.get(i));
            iExample.setValue(atts.get(1), temperatureValue.get(i));
            iExample.setValue(atts.get(2), humidityValue.get(i));
            iExample.setValue(atts.get(3), windyValue.get(i));
            iExample.setValue(atts.get(4), result.get(i));
            isTrainingSet.add(Utils.missingValue());
        }

        Instances testSet = new Instances("test", atts, 10);
        for (int i = 0; i < result.size(); i++) {
            Instance iExample = new DenseInstance(5);
            iExample.setValue(atts.get(0), outlookValue.get(i));
            iExample.setValue(atts.get(1), temperatureValue.get(i));
            iExample.setValue(atts.get(2), humidityValue.get(i));
            iExample.setValue(atts.get(3), windyValue.get(i));
            iExample.setMissing(i);
            testSet.add(iExample);
        }

        NaiveBayes naiveBayesbis = new NaiveBayes();
        naiveBayesbis.buildClassifier(isTrainingSet);

        System.out.println("testSEt size ->" + testSet);
        for (int i = 0; i < testSet.size(); i++) {
            System.out.println("testSEt instance ->" + testSet.instance(i));

            double label = naiveBayesbis.classifyInstance(testSet.instance(0));
            double[] predictionDistribution = naiveBayesbis.distributionForInstance(testSet.instance(0));
            testSet.instance(i).setClassValue(label);
            System.out.println(testSet.get(i).toString(0)
                    + " : " + testSet.get(i).toString(1)
                    + " : " + testSet.get(i).toString(2)
                    + " : " + testSet.get(i).toString(3)
                    + " : " + testSet.instance(0).toString(4)
                    + " : " + Double.toString(predictionDistribution[0]));
        }

//        //build a J48 decision tree
//        J48 model = new J48();
//        model.buildClassifier(test);
//
//        //decide which instance you want to predict
//        int s1 = 2;
//
//        //get the predicted probabilities
//        double[] prediction = model.distributionForInstance(test.get(s1));
//        //output predictions
//        for (int i = 0; i < prediction.length; i = i + 1) {
//            System.out.println("Probability of class " +
//                    test.classAttribute().value(i) +
//                    " : " + Double.toString(prediction[i]));
//        }
    }


}