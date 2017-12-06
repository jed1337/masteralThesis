## What it does
It uses a hybrid approach: it first identifies if the traffic is normal or an attack, if it is an attack, it identifies if it is part of a high rate or low rate DDoS attack

## Input
Normal, high rate, and low rate data whose features were extracted using [KDD99 Feature extractor](https://github.com/AI-IDS/kdd99_feature_extractor)

## Output
Classification

## Specifics
### Tools used:
* [Weka](https://www.cs.waikato.ac.nz/ml/weka/)
* [Low Orbit Ion Cannon](https://github.com/NewEraCracker/LOIC)
* [Wireshark](wireshark.org/)
* [Apache Benchmark](https://httpd.apache.org/docs/2.4/programs/ab.html)

### Uses 5 machine learning algorithms:
* J48
* IBk
* NaiveBayes
* RandomForest
* SMO

# Things to do
* Miscellaneous
	* Show warning if file already exists
	* Automate setting folder path based on configuration

* Feature selection, only select features from the train set
	* During testing, apply that to the test set.

* Classify
		* Create a train model function

* Add JUnit tests
	* Use checksum to verify that the files are the same

* Make the code more OO
	* Structure
		* Preprocess data
		* Combine data
		* Split into test train validation
		* Feature selection on train set
		* //Apply the selected features to train and validation
		* Train classifier
		* Test classifier
		* Record results
	* Abstract structure
		* Preprocess
			* Format files
			* Setup train test validation
		* Feature selection (Give only trianing data)
		* Classifiers
		* Evaluate