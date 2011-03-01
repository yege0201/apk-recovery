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


/**
 * Helper class for apktool.
 * 
 * @author justinkode@gmail.com
 *
 */
public class ADApktoolUtil {

	private static ADApktoolUtil instance;
	
	private File apktoolDirectory;
	
	private ADApktoolUtil(){
		StringBuilder sb = new StringBuilder(ADUtils.getInstance().getToolsDirectoryLocation());
		sb.append(File.separatorChar);
		sb.append(IADConstants.TOOLS_APKTOOL_DIR_NAME);
		apktoolDirectory = new File(sb.toString());
	}
	
	public static ADApktoolUtil getInstance(){
		if(instance == null)instance = new ADApktoolUtil();
		return instance;
	}
	
	/**
	 * Processes the apk file which absolute path is given in parameter.
	 * 
	 * @param apkFilePath
	 */
	public void processApkFile(String apkFilePath){
		//handle an reference on apk file to process
		StringBuilder sb = new StringBuilder(ADUtils.getInstance().getRootDirectory().getAbsolutePath());
		sb.append(File.separatorChar);
		sb.append(apkFilePath);
		
		//process apk file using apktool
		StringBuilder sbApktoolShell = new StringBuilder(apktoolDirectory.getAbsolutePath());
		sbApktoolShell.append(File.separatorChar);
		sbApktoolShell.append(IADConstants.TOOLS_APKTOOL_SHELL_NAME);
		String apktoolShell = sbApktoolShell.toString();
		String os = System.getProperty("os.name").toLowerCase();
        if (os.indexOf("win") >= 0) {
        	apktoolShell+=IADConstants.BAT_EXT;
        }
		
		StringBuilder sbCommand = new StringBuilder(apktoolShell);
		sbCommand.append(" d -f ");
		sbCommand.append(apkFilePath);
		sbCommand.append(" ");
		sbCommand.append(ADUtils.getInstance().getWorkingDirectory().getAbsolutePath());
		if (os.indexOf("win") == -1) {
			String cmd = sbCommand.toString();
			sbCommand = new StringBuilder("sh");
			sbCommand.append(" ");
			sbCommand.append(cmd);
        }
		
		
		Process p = null;
		try {
			p=Runtime.getRuntime().exec(sbCommand.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			p.waitFor();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		
	}

	public File getApktoolDirectory() {
		return apktoolDirectory;
	}
	
	
	
}
