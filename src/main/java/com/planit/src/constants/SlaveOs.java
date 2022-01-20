package com.planit.src.constants;

public enum SlaveOs {
	LINUX, WINDOWS, MAC;

	private static String osType = System.getProperty("os.name").toLowerCase();
	private static SlaveOs slaveOs;

	private static boolean isWindows() {
		return (osType.contains("win"));
	}

	private static boolean isMac() {
		return (osType.contains("mac"));
	}

	private static boolean isLinux(){
		return (osType.contains("nix") || osType.contains("nux") || osType.contains("aix"));
	}

	private static void determineSlaveOsType() {

		if (isWindows())
			slaveOs = SlaveOs.WINDOWS;
		else if (isLinux())
			slaveOs = SlaveOs.LINUX;
		else if (isMac())
			slaveOs = SlaveOs.MAC;
		else
			throw new RuntimeException("Unknown OS , currently we are supporting mac,linux or windows");

	}

	public static SlaveOs getOs() {
		if (null == slaveOs) {
			determineSlaveOsType();
		}
		return slaveOs;
	}

}
