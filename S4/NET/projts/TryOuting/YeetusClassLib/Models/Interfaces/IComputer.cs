namespace YeetusClassLib.Models.Interfaces
{
	public interface IComputer
	{
		string Brand { get; set; }
		Color DeviceColor { get; set; }
		bool? PoweredOn { get; }

		void ExecuteProgram(string program);
		void PowerOn();
		string ToString();
	}
}