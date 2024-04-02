//Object Initializer
Laptop laptopOfJens = new Laptop()
{
    Brand = "Acer",
    Id = 3,
    DeviceColor = Color.Red,
    Resolution = 15.4
};
laptopOfJens.OpenLid();
laptopOfJens.ExecuteProgram("Visual Studio");
laptopOfJens.ExecuteProgram("Visual Studio Code");
laptopOfJens.ChargeBattery();
laptopOfJens.CloseLid();
Console.WriteLine(laptopOfJens);
Desktop desktopOfJenny = new()
{
    Brand = "Dell",
    DeviceColor = Color.Beige
}; //Inferred Type (C# 9)
Console.WriteLine(desktopOfJenny);

string computerColor = laptopOfJens.DeviceColor switch
{
    Color.Transparant or Color.White or Color.Black => $"The {nameof(Computer)} has a regular color ",
    Color.Red or Color.Blue => $"The {nameof(Computer)} has a flashy color",
    Color.Grey or Color.Beige => $"The {nameof(Computer)} has a boring color",
    _ => $"The {nameof(Computer)} has a no color"
};
