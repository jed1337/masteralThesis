package constants;

public enum FileNameConstants{
	CROSSFOLD_NAME       ("Crossfold.arff"),
   
	HEADER               ("Header.arff"),

	KDD_TEST             ("KDDTest+.arff"),
	KDD_TEST_MINUS_21    ("KDDTest-21.arff"),
	KDD_TRAIN            ("KDDTrain+.arff"),
	KDD_TRAIN_20_PERCENT ("KDDTrain+_20Percent.arff"),

	CNIS_HIGHRATE        ("CNISHighrate.arff"),
	CNIS_LOWRATE         ("CNISLowrate.arff"),
	CNIS_NOISE           ("CNISNoise.arff");

	private final String VALUE;

	private FileNameConstants(String value){
		this.VALUE = value;
	}

   @Override
   public String toString() {
      return VALUE;
   }
}