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
package fr.elh.tools.apk.util;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

/**
 * Helper which contains various common helper methods.
 * 
 * @author justinkode@gmail.com
 * 
 */
public class ADUtils {

	private String toolsDirectoryLocation;

	private File rootDirectory;

	private File workingDirectory;

	private File dexFile;

	private File tmpClassesDir;

	private static ADUtils instance;

	private ADUtils() {

	}

	public static ADUtils getInstance() {
		if (instance == null) {
			instance = new ADUtils();
		}
		return instance;
	}

	/**
	 * Initializes program.
	 * 
	 * @param apkPath
	 * @throws IllegalStateException
	 */
	public void initRecovery(String apkPath) throws IllegalStateException {
		// 1 - do raw checks on parameters
		if (apkPath == null) {
			throw new IllegalStateException(
					"[Error]: program arguments cannot be null!");
		}
		// 2 -check .apk file existence
		File apkFile = null;
		apkFile = new File(apkPath);
		if (!apkFile.isFile()) {
			throw new IllegalStateException(
					"[Error]: Could not find apk file for path: " + apkPath);
		}
		if (!FilenameUtils.getExtension(apkFile.getName()).equals("apk")) {
			throw new IllegalStateException(
					"[Error]: Can only process .apk files!");
		}
		// 3 - compute tools directory location
		computeToolsDirectory();
		// 4 - create the time stamped working directory
		createWorkingDirectory(FilenameUtils.getBaseName(apkFile.getName()));

	}

	/**
	 * Computes the classes.dex file.
	 * 
	 * @return
	 */
	public void computeDexFile() {
		StringBuilder sb = new StringBuilder(workingDirectory.getAbsolutePath());
		sb.append(File.separatorChar);
		sb.append(IADConstants.DEX_CLASSES_FILE_NAME);
		dexFile = new File(sb.toString());
	}

	/**
	 * Creates the working directory using the apk file name given in parameter.
	 * 
	 * @param apkFileName
	 */
	private void createWorkingDirectory(String apkFileName) {
		StringBuilder sb = new StringBuilder(apkFileName);
		sb.append("_");
		sb.append(System.currentTimeMillis());
		workingDirectory = new File(sb.toString());
		workingDirectory.mkdir();
	}

	/**
	 * Creates a temporary folder for java classes extracted using dex2jar tool.
	 */
	public void createTmpClassesDirectory() {
		StringBuilder sb = new StringBuilder(workingDirectory.getAbsolutePath());
		sb.append(File.separatorChar);
		sb.append(IADConstants.TMP_CLASSES_BASE_NAME);
		sb.append("_");
		sb.append(System.currentTimeMillis());
		tmpClassesDir = new File(sb.toString());
		tmpClassesDir.mkdir();
	}

	/**
	 * Computes the tools directory.
	 * 
	 */
	private void computeToolsDirectory() {
		File dir = new File(".");
		String rootDirPath = dir.getAbsolutePath().substring(0, dir.getAbsolutePath().length() -2) ;
		this.rootDirectory = new File(rootDirPath);
		
		toolsDirectoryLocation = rootDirectory.getAbsolutePath();
		toolsDirectoryLocation += File.separatorChar;
		toolsDirectoryLocation += IADConstants.TOOLS_DIR_NAME;

		File toolsDirectory = null;
		toolsDirectory = new File(toolsDirectoryLocation);
				
		if (toolsDirectory.isDirectory()) {
			this.toolsDirectoryLocation = toolsDirectory.getAbsolutePath();
		} else {
			throw new IllegalStateException(
					"[Error]: Could not find tools directory!");
		}

	}

	/**
	 * Removes all temporary resources after recovery process
	 */
	public void removeTemporaryResources() {
		StringBuilder sb = null;
		
		sb = new StringBuilder(getWorkingDirectory().getAbsolutePath());
		sb.append(File.separatorChar);
		
		// remove tmp classes directory
		try {
			FileUtils.deleteDirectory(getTmpClassesDir());
		} catch (IOException e) {
			e.printStackTrace();
		}

		// remove classes.dex file
		sb = new StringBuilder(getWorkingDirectory().getAbsolutePath());
		sb.append(File.separatorChar);
		sb.append(IADConstants.DEX_CLASSES_FILE_NAME);
		File classesDexFile = new File(sb.toString());
		FileUtils.deleteQuietly(classesDexFile);

		// remove apktool.yml file
		sb = new StringBuilder(getWorkingDirectory().getAbsolutePath());
		sb.append(File.separatorChar);
		sb.append(IADConstants.TEMP_RES_APKTOOL_YML_FILE_NAME);
		File ymlFile = new File(sb.toString());
		FileUtils.deleteQuietly(ymlFile);
	}

	/**
	 * Copy the jd-gui.exe file into working directory.
	 * 
	 */
	public void copyJDGuiToWorkingDirectory() {
		StringBuilder sb = new StringBuilder(getToolsDirectoryLocation());
		sb.append(File.separatorChar);
		sb.append(IADConstants.TOOLS_JDGUI_DIR_NAME);
		sb.append(File.separatorChar);
		String os = System.getProperty("os.name").toLowerCase();
        if (os.indexOf("win") >= 0) {
        	sb.append(IADConstants.TOOLS_JDGUI_FILE_NAME_EXE);
        	File jdGuiExeFile = new File(sb.toString());
    		try {
    			FileUtils.copyFileToDirectory(jdGuiExeFile, getWorkingDirectory());
    		} catch (IOException e) {
    			e.printStackTrace();
    		}
        }else{
        	String jdGuiShToolDir = sb.toString();
        	//configure jd-gui copy paths
        	StringBuilder sbJdGuiLinuxHelperPath = new StringBuilder(jdGuiShToolDir);
        	sbJdGuiLinuxHelperPath.append(File.separatorChar);
        	sbJdGuiLinuxHelperPath.append(IADConstants.TOOLS_JDGUI_FILE_NAME_SH);
        	File jdGuiShFile = new File(sbJdGuiLinuxHelperPath.toString());
        	
        	sbJdGuiLinuxHelperPath = new StringBuilder(jdGuiShToolDir);
        	sbJdGuiLinuxHelperPath.append(File.separatorChar);
        	sbJdGuiLinuxHelperPath.append(IADConstants.TOOLS_JDGUI_CONTRIB_DIR_NAME);
        	File jdGuiShContrib = new File(sbJdGuiLinuxHelperPath.toString());
    		try {
    			FileUtils.copyFileToDirectory(jdGuiShFile, getWorkingDirectory());
    			jdGuiShFile = new File(getWorkingDirectory().getAbsoluteFile().toString()+File.separatorChar+IADConstants.TOOLS_JDGUI_FILE_NAME_SH);
    			jdGuiShFile.setExecutable(true);
    			FileUtils.copyDirectoryToDirectory(jdGuiShContrib, getWorkingDirectory());
    		} catch (IOException e) {
    			e.printStackTrace();
    		}
        }
		
	}

	public File getDexFile() {
		if (dexFile == null)
			computeDexFile();
		return dexFile;
	}

	public String getToolsDirectoryLocation() {
		return toolsDirectoryLocation;
	}

	public File getWorkingDirectory() {
		return workingDirectory;
	}

	public File getRootDirectory() {
		return rootDirectory;
	}

	public File getTmpClassesDir() {
		return tmpClassesDir;
	}

}
