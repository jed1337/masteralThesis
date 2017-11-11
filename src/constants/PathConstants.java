package constants;

public enum PathConstants{
   UNFORMATTED_DIR ("Data/RawFiles/"),
   FORMATTED_DIR   ("Data/FormattedFiles/");

	private final String value;

	private PathConstants(String value){
		this.value = value;
	}

   @Override
   public String toString(){
      return value;
   }
}