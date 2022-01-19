package com.test.src.constants;

public enum SlaveOs {
	LINUX, WINDOWS, MAC;

	private static String osType = System.getProperty("os.name").toLowerCase();
	private static SlaveOs slaveOs;

	private static boolean isWindows() throws Exception {
		return (osType.indexOf("win") >= 0);
	}

	private static boolean isMac() throws Exception {
		return (osType.indexOf("mac") >= 0);
	}

	private static boolean isLinux() throws Exception {
		return (osType.indexOf("nix") >= 0 || osType.indexOf("nux") >= 0 || osType.indexOf("aix") >= 0);
	}

	private static void determineSlaveOsType() throws Exception {

		if (isWindows())
			slaveOs = SlaveOs.WINDOWS;
		else if (isLinux())
			slaveOs = SlaveOs.LINUX;
		else if (isMac())
			slaveOs = SlaveOs.MAC;
		else
			throw new RuntimeException("Unknown OS , currently we are supporting mac,linux or windows");

	}

	public static SlaveOs getOs() throws Exception {
		if (null == slaveOs) {
			determineSlaveOsType();
		}
		return slaveOs;
	}

}
