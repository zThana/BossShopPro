package org.black_ixx.bossshop.managers.config;

import org.apache.commons.io.FileUtils;
import org.black_ixx.bossshop.managers.ClassManager;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;

public class ConfigKeyChecker {
    public static void checkConfig(){
        InputStream is = ClassManager.manager.getPlugin().getResource("config.yml");
        assert is != null;
        File f = new File(ClassManager.manager.getPlugin().getDataFolder(),"config.yml");
        File f2; // config.yml in resources folder
        try {f2 = File.createTempFile("config-cache","yml");//make cache
            FileUtils.copyInputStreamToFile(is, f2);
        } catch (IOException e) {
            throw new RuntimeException(e);}
        FileConfiguration c = YamlConfiguration.loadConfiguration(f);
        FileConfiguration c2 = YamlConfiguration.loadConfiguration(f2);
        Set<String> key = c.getKeys(true);
        Set<String> key2 = c2.getKeys(true);
        Set<String> willAdds = new HashSet<>(key);
        willAdds.removeAll(key2);
        for(String cfg_key:willAdds){
            Object value = c2.get(cfg_key);
            c.set(cfg_key,value);
        }
        f2.delete();
    }
    private static void checkEnUsLanguage(){
        InputStream is = ClassManager.manager.getPlugin().getResource("lang/en_us.yml");
        assert is != null;
        File f = new File(ClassManager.manager.getPlugin().getDataFolder(),"lang/en_us.yml");
        File f2;
        try {f2 = File.createTempFile("en_us-lang-cache","yml");//make cache
            FileUtils.copyInputStreamToFile(is, f2);
        } catch (IOException e) {
            throw new RuntimeException(e);}
        FileConfiguration c = YamlConfiguration.loadConfiguration(f);
        FileConfiguration c2 = YamlConfiguration.loadConfiguration(f2);
        Set<String> key = c.getKeys(true);
        Set<String> key2 = c2.getKeys(true);
        Set<String> willAdds = new HashSet<>(key);
        willAdds.removeAll(key2);
        for(String cfg_key:willAdds){
            String value = c2.getString(cfg_key);
            c.set(cfg_key,value);
        }
        f2.delete();
    }
    public static void checkLanguages(){
        checkEnUsLanguage();
        checkZhCnLanguage();
    }
    private static void checkZhCnLanguage(){
        InputStream is = ClassManager.manager.getPlugin().getResource("lang/zh_cn.yml");
        assert is != null;
        File f = new File(ClassManager.manager.getPlugin().getDataFolder(),"lang/zh_cn.yml");
        File f2;
        try {f2 = File.createTempFile("zh_cn-lang-cache","yml");//make cache
            FileUtils.copyInputStreamToFile(is, f2);
        } catch (IOException e) {
            throw new RuntimeException(e);}
        FileConfiguration c = YamlConfiguration.loadConfiguration(f);
        FileConfiguration c2 = YamlConfiguration.loadConfiguration(f2);
        Set<String> key = c.getKeys(true);
        Set<String> key2 = c2.getKeys(true);
        Set<String> willAdds = new HashSet<>(key);
        willAdds.removeAll(key2);
        for(String cfg_key:willAdds){
            String value = c2.getString(cfg_key);
            c.set(cfg_key,value);
        }
        f2.delete();
    }
}
