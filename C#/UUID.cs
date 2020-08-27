using System;
using System.Collections.Generic;
using System.Management;
using System.Text;

namespace Uchart
{
    public class UUID
    {
        public static string Get()
        {
            string uuid = string.Empty;

            ManagementClass mc = new ManagementClass("Win32_ComputerSystemProduct");
            ManagementObjectCollection moc = mc.GetInstances();

            foreach (ManagementObject mo in moc)
            {
                uuid = mo.Properties["UUID"].Value.ToString();
                break;
            }

            return uuid;
        }
    }
}
