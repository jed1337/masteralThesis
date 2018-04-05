# Training and testing
It first identifies if the traffic is normal or an attack, if it is an attack, it identifies if it is part of a high rate or low rate DDoS attack. The system can be configured to provide the specific attack name.

## Input
Normal, high rate, and low rate data whose features were extracted using [KDD99 Feature extractor](https://github.com/AI-IDS/kdd99_feature_extractor)

## Output
Classification

## Training system specifics
### Tools used:
* [Weka](https://www.cs.waikato.ac.nz/ml/weka/)
* [Wireshark](wireshark.org/)
* [Low Orbit Ion Cannon](https://github.com/NewEraCracker/LOIC)
* [SlowHTTPTest](https://github.com/shekyan/slowhttptest)
* [Hping](https://github.com/antirez/hping)

### Uses 5 machine learning algorithms:
* J48
* IBk
* Naive Bayes
* Random Forest
* SMO

### 5 feature selection methods
* None (baseline)
* Information gain
* Attribute correlation
* J48 wrapper
* Naive Bayes wrapper

### Database compatibility
* Mysql

# Live system specifics

## Input
A trained model

## Output
Probability of being in a certain class as well as the IP addresses involved in the flow