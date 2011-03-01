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
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.apache.commons.io.IOUtils;

/**
 * Helper used to uncompress resources.
 * 
 * @author justinkode@gmail.com
 * 
 */
public class ADZipUtil {

	private static ADZipUtil instance;

	private ADZipUtil() {

	}

	public static ADZipUtil getInstance() {
		if (instance == null) {
			instance = new ADZipUtil();
		}
		return instance;
	}

	/**
	 * Extracts the apk file given in parameter into the working directory.
	 * 
	 * @param apkFile
	 */
	public void extractApkFile(File apkFile) {
		try {
			extractZip(apkFile, ADUtils.getInstance().getWorkingDirectory());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Extracts the classes.dex from apk file into working directory.
	 * 
	 * @param apkFile
	 * @param fileNameToExtract
	 */
	public void extractClassesDexFile(File apkFile, String fileNameToExtract) {
		try {
			extractResourceFromZip(apkFile, ADUtils.getInstance()
					.getWorkingDirectory(), fileNameToExtract);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param f
	 * @param unzipLocation
	 * @param fileNameToExtract
	 * @throws IOException
	 */
	private void extractResourceFromZip(File f, File unzipLocation,
			String fileNameToExtract) throws IOException {
		ZipFile zip = new ZipFile(f);
		try {
			Enumeration<?> entries = zip.entries();
			while (entries.hasMoreElements()) {
				ZipEntry entry = (ZipEntry) entries.nextElement();
				if (entry.getName().equals(fileNameToExtract)) {
					File file = new File(unzipLocation, entry.getName());
					if (entry.isDirectory()) {
						file.mkdirs();
						continue;
					} else if (!file.getParentFile().exists()) {
						file.getParentFile().mkdirs();
					}

					FileOutputStream fos = new FileOutputStream(file);
					try {
						IOUtils.copy(zip.getInputStream(entry), fos);
					} finally {
						IOUtils.closeQuietly(fos);
					}
				}

			}
		} finally {
			zip.close();
		}
	}

	/**
	 * Extracts the content of the file into the unzip location given in
	 * parameter .
	 * 
	 * @param f
	 * @param unzipLocation
	 * @throws IOException
	 */
	private void extractZip(File f, File unzipLocation) throws IOException {
		ZipFile zip = new ZipFile(f);
		try {
			Enumeration<?> entries = zip.entries();
			while (entries.hasMoreElements()) {
				ZipEntry entry = (ZipEntry) entries.nextElement();
				File file = new File(unzipLocation, entry.getName());
				if (entry.isDirectory()) {
					file.mkdirs();
					continue;
				} else if (!file.getParentFile().exists()) {
					file.getParentFile().mkdirs();
				}

				FileOutputStream fos = new FileOutputStream(file);
				try {
					IOUtils.copy(zip.getInputStream(entry), fos);
				} finally {
					IOUtils.closeQuietly(fos);
				}
			}
		} finally {
			zip.close();
		}
	}

}
