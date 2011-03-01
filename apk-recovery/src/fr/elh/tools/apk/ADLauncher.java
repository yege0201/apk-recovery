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
package fr.elh.tools.apk;

import java.io.File;

import fr.elh.tools.apk.tools.ADApktoolUtil;
import fr.elh.tools.apk.tools.ADDex2JarUtil;
import fr.elh.tools.apk.util.ADUtils;
import fr.elh.tools.apk.util.ADZipUtil;
import fr.elh.tools.apk.util.IADConstants;

/**
 * Apk file recovery entry point.
 * 
 * @author justinkode@gmail.com
 * 
 */
public class ADLauncher {
	/**
	 * The apk file to process.
	 */
	private static File apkFile;

	public static void main(String[] args) {
		if ((args.length != 1) && (args.length != 2)) {
			throw new IllegalStateException(
					"[Error]: usage: apkRecovery <apk file path>");
		}
		apkFile = new File(args[0]);
		recoverApk();

	}

	/**
	 * Recovers an apk file.
	 */
	private static void recoverApk() {
		System.out.println("Apk recovery process started...\n");
		System.out.println("\t1/6: Configuration settings step started...");
		// 1 - initialize context
		try {
			ADUtils.getInstance().initRecovery(apkFile.getAbsolutePath());
		} catch (IllegalStateException e) {
			e.printStackTrace();
			System.exit(2);
		}
		System.out.println("\tConfiguration settings step done...\n");
		// 2 - process apk file using apktool
		System.out.println("\t2/6: Apktool step started...");
		ADApktoolUtil.getInstance().processApkFile(apkFile.getAbsolutePath());
		System.out.println("\tApktool step done...\n");

		// 3 - extract xlasses.dex file from apk file
		System.out.println("\t3/6: classes.dex extraction step started...");
		ADZipUtil.getInstance().extractClassesDexFile(apkFile,
				IADConstants.DEX_CLASSES_FILE_NAME);
		System.out.println("\tclasses.dex extraction step done...\n");

		// 4 - process dex file using dex2jar
		System.out.println("\t4/6: Dex2jar step started...");
		ADDex2JarUtil.getInstance().processDexFile();
		System.out.println("\tDex2jar step done...\n");

		// 5 - copy jd-gui class file decompiler into working directory
//		if (System.getProperty("os.name").toLowerCase().indexOf("win") == -1) {
//			System.out
//					.println("\t5/6: Skipping step: linux users must use their favorite java decompiler to reverse \"classes.dex.dex2jar.jar\" file...");
//		} else {
//			System.out
//					.println("\t5/6: Copying jd-gui tool into working directory step started...");
//			ADUtils.getInstance().copyJDGuiToWorkingDirectory();
//			System.out
//					.println("\tCopying jd-gui tool into working directory step done...\n");
//		}
		
		// 5 - copy jd-gui class file decompiler into working directory
		
			System.out
					.println("\t5/6: Copying jd-gui tool into working directory step started...");
			ADUtils.getInstance().copyJDGuiToWorkingDirectory();
			System.out
					.println("\tCopying jd-gui tool into working directory step done...\n");
		

		// 6 - remove temporary resources
		System.out
				.println("\t6/6: Sweeping temporary resources step started...");
		ADUtils.getInstance().removeTemporaryResources();
		System.out.println("\tSweeping temporary resources step done...\n");

		System.out.println("Apk recovery process finished!");

		System.out.println("Recovery output location: "
				+ ADUtils.getInstance().getWorkingDirectory().getAbsolutePath()
				+ "\n");

	}

}
