package com.example.demo.install;
import android.content.Context;
import android.content.pm.IPackageInstallObserver;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.IBinder;
import android.os.RemoteException;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
  
public class ApplicationManager {  
    public final int INSTALL_REPLACE_EXISTING = 2;  
    public static final int INSTALL_SUCCEEDED = 1;  
  
    private PackageInstallObserver observer;  
    private PackageManager pm;  
    private Method method;  
      
    private OnInstalledPackaged onInstalledPackaged;  
      
   /* class PackageInstallObserver extends IPackageInstallObserver.Stub {  
  
        public void packageInstalled(String packageName, int returnCode) throws RemoteException {  
            if (onInstalledPackaged != null) {  
                onInstalledPackaged.packageInstalled(packageName, returnCode);  
            }  
        }  
    }*/  
    
    private static class PackageInstallObserver implements IPackageInstallObserver{

		@Override
		public IBinder asBinder() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void packageInstalled(String arg0, int arg1)
				throws RemoteException {
			// TODO Auto-generated method stub
		}  
         
    } 
      
    public ApplicationManager(Context context) throws SecurityException, NoSuchMethodException {  
          
        observer = new PackageInstallObserver();  
        pm = context.getPackageManager();  
          
        Class<?>[] types = new Class[] {Uri.class, IPackageInstallObserver.class, int.class, String.class};  
        method = pm.getClass().getMethod("installPackage", types);  
    }  
      
    public void setOnInstalledPackaged(OnInstalledPackaged onInstalledPackaged) {  
        this.onInstalledPackaged = onInstalledPackaged;  
    }  
  
    public void installPackage(String apkFile) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {  
        installPackage(new File(apkFile));  
    }  
      
    public void installPackage(File apkFile) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {  
        if (!apkFile.exists()) throw new IllegalArgumentException();  
        Uri packageURI = Uri.fromFile(apkFile);  
        installPackage(packageURI);  
    }  
      
    public void installPackage(Uri apkFile) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {  
        method.invoke(pm, new Object[] {apkFile, observer, INSTALL_REPLACE_EXISTING, null});  
    }  
      
}  