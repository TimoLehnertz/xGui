package xGui;

public class PresetStep {
	
	public static final boolean X = true;
	public static final boolean Y = false;

	public String name;
	public double splitPercentage;
	public PresetStep leftContent;
	public PresetStep rightContent;
	public boolean splitX;
	
	public PresetStep(String name, double splitPercentage, boolean splitX) {
		this(name, splitPercentage, splitX, null, null);
	}
	
	public PresetStep(String name, double splitPercentage, boolean splitX, PresetStep leftContent, PresetStep rightContent) {
		super();
		this.name = name;
		this.splitPercentage = splitPercentage;
		this.splitX = splitX;
		this.leftContent = leftContent;
		this.rightContent = rightContent;
	}
}