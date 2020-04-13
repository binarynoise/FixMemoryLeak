/*
 * Copyright (c) 2020 @binarynoise.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package de.binarynoise.fixMemoryLeak;

import android.view.ViewGroup;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

import java.lang.reflect.Field;

import static de.robv.android.xposed.XposedHelpers.findClassIfExists;

@SuppressWarnings("unused")
public class Hook implements IXposedHookLoadPackage {
	
	@Override
	public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {
		Class<?> viewLocationHolderClass = findClassIfExists("android.view.ViewGroup$ViewLocationHolder", lpparam.classLoader);
		
		if (viewLocationHolderClass == null)
			for (Class<?> cls : ViewGroup.class.getDeclaredClasses())
				if (cls.getSimpleName().equals("ViewLocationHolder")) {
					viewLocationHolderClass = cls;
					break;
				}
		
		final Field mRoot = viewLocationHolderClass.getDeclaredField("mRoot");
		mRoot.setAccessible(true);
		
		XposedBridge.hookAllMethods(viewLocationHolderClass, "clear", new XC_MethodHook() {
			@Override
			protected void afterHookedMethod(MethodHookParam param) throws Throwable {
				//mRoot = null;
				mRoot.set(param.thisObject, null);
			}
		});
	}
}
