/*
 *   Copyright (C) 2011 justinkode.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *      
 *	Unless required by applicable law or agreed to in writing, software
 *	distributed under the License is distributed on an "AS IS" BASIS,
 *	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *	See the License for the specific language governing permissions and
 *	limitations under the License.
 *
 */
package fr.elh.tools.apk.tools;

import java.io.File;
import java.io.IOException;

import fr.elh.tools.apk.util.ADUtils;
import fr.elh.tools.apk.util.IADConstants;


public class ADDex2JarUtil {
	
	private static ADDex2JarUtil instance;
	
	private File dex2JarDir;
	
	private ADDex2JarUtil(){
		//retrieve dex2jar location
		StringBuilder sb = new StringBuilder(ADUtils.getInstance().getToolsDirectoryLocation());
		sb.append(File.separatorChar);
		sb.append(IADConstants.TOOLS_DEX2JAR_DIR_NAME);
		dex2JarDir = new File(sb.toString());
		//create temporary folder for classes 
		ADUtils.getInstance().createTmpClassesDirectory();
	}
	
	public static ADDex2JarUtil getInstance(){
		if(instance == null){
			instance = new ADDex2JarUtil();
		}
		return instance;
	}
	
	/**
	 * Processes the classes.dex file.
	 */
	public void processDexFile(){
		File dexFile = ADUtils.getInstance().getDexFile();
		StringBuilder sb = new StringBuilder(dex2JarDir.getAbsolutePath());
		sb.append(File.separatorChar);
		sb.append(IADConstants.DEX2JAR_SHELL_NAME);
		String os = System.getProperty("os.name").toLowerCase();
		if (os.indexOf("win") >= 0) {
			sb.append(IADConstants.BAT_EXT);
        } else {
        	sb.append(IADConstants.SH_EXT);
        }
		
		StringBuilder sbCmd = new StringBuilder(sb.toString());
		sbCmd.append(" ");
		sbCmd.append(dexFile.getAbsolutePath());
		
		if (os.indexOf("win") == -1) {
			String cmd = sbCmd.toString();
			sbCmd = new StringBuilder("sh");
			sbCmd.append(" ");
			sbCmd.append(cmd);
        }
		
		Process proc = null;
		try {
			proc = Runtime.getRuntime().exec(sbCmd.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			proc.waitFor();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
