package controller;

public interface EventCommand {
	public String className();
	public void execute();
	public void register();
}
