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
	* Automate setting folder path based on configuration
	* Clean up driver code

* Classify
		* Create a train model function

* Add JUnit tests
	* Use checksum to verify that the files are the same
	* Add more tests in general

* Database
	* Clean DB code