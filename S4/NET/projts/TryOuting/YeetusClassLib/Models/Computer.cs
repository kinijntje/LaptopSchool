using YeetusClassLib.Models;

namespace YeetusClassLib;
public class Computer : ComputerBase
{
	public string Brand { get; set; } = "HP";
	public virtual Color DeviceColor { get; set; } = Color.Beige;
	public bool? PoweredOn { get; private set; }


	public Computer()
	{
		Id = new Random().Next(1, 2000);
	}

	public void PowerOn()
	{
		PoweredOn ??= true;
	}

	public virtual void ExecuteProgram(string program)
	{
		PowerOn();
		Console.WriteLine($"{program} is executed");
	}

	public override string ToString()
	{
		return $"brand: {Brand}\n color: {DeviceColor}\n poweredOn: {PoweredOn}";
	}
}

