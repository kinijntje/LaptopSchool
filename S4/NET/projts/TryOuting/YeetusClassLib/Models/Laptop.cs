namespace YeetusClassLib.Models
{
	public class Laptop: Computer
	{
		public double Resolution { get; set; } = 13.3;
		public double BatteryLevel { get; private set; }
		public override Color DeviceColor { get; set; } = Color.Black;

		public Laptop()
		{

		}

		public void OpenLid()
		{
			Console.WriteLine("Lid is now open");
			PowerOn();
		}

		public void CloseLid()
		{
			Console.WriteLine("Lid is now closed");
		}

		public void ChargeBattery()
		{
			BatteryLevel = (BatteryLevel > 99) ? 100 : ++BatteryLevel;
		}

		public override void ExecuteProgram(string program)
		{
			base.ExecuteProgram(program);
			BatteryLevel = (BatteryLevel > 0) ? --BatteryLevel : 0;
		}
	}
}
