package ru.ecom.jaas.ejb.service;

import org.apache.log4j.Logger;
import ru.ecom.ejb.util.injection.EjbEcomConfig;
import ru.ecom.jaas.ejb.domain.SoftConfig;

import javax.annotation.Resource;
import javax.ejb.Remote;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Stateless
@Remote(ISoftConfigService.class )
public class SoftConfigServiceBean implements ISoftConfigService {
	private static final Logger LOG = Logger.getLogger(SoftConfigServiceBean.class);
	public static String getDefaultParameterByConfig(String aParameter, String aValueDefault, EntityManager aManager) {
		List<Object[]> l = aManager.createNativeQuery("select sf.id,sf.keyvalue from SoftConfig sf where  sf.key=:key").setParameter("key",aParameter).getResultList();
		return l.isEmpty() ? aValueDefault : l.get(0)[1].toString();
	}

	public String getDir(String aKey, String aDefaultValue) {
		EjbEcomConfig config = EjbEcomConfig.getInstance() ;
		return config.get(aKey, aDefaultValue) ;
	}
	public void saveContextHelp(String aUrl,String aContext) {
		File file = getFile(aUrl, true) ;
		try (PrintWriter out = new PrintWriter(new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8))) {
            out.println(aContext) ;
		} catch (FileNotFoundException e) {
			LOG.error(e);
		}
	}
	private File getFile(String aUrl,boolean aIsCreateDir) {
		aUrl=aUrl.replace("/WEB-INF/actions", "/riams") ;
		aUrl=aUrl.replace(".jsp", ".htm") ;
		String dirName = getDir("help_riams", "/opt/tomcat/webapps/help") ;
		dirName= dirName+aUrl ;
		String[] u = dirName.split("/") ;
		String f =u[u.length-1] ;
		dirName=dirName.replace("/"+f, "") ;
		if (aIsCreateDir) {
			File dir = new File(dirName) ;
			if (dir.exists()) {
				LOG.info("exists dir");
			} else {
				LOG.info("create dir="+dir.mkdirs());
			}
		}
		return  new File(dirName,f) ;
	}
	public String getContextHelp(String aUrl) {
		StringBuilder sb =new StringBuilder() ;
		File file = getFile(aUrl, false) ;
		if (file.exists()) {
			try (LineNumberReader in = new LineNumberReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {
				String line;
				while ((line = in.readLine()) != null) {
					sb.append(line);
				}
			} catch (IOException e) {
				LOG.error(e);
			}
		}
		return sb.toString() ;
		
	}

	@Override
	public String getConfigValue(String key) {
		List<Object[]> l = manager.createNativeQuery("select sf.id,sf.keyvalue from SoftConfig sf where  sf.key=:key").setParameter("key",key).getResultList();
		return l.isEmpty() ? null : l.get(0)[1].toString();
	}

	public void addConfigDefaults() {
		Map<String,String> defaultConfig = create() ;
		findOrCreateSoftConfig("data_base_namespace", defaultConfig) ;
	}

	private SoftConfig findOrCreateSoftConfig(String aKey, Map<String,String> aDefault) {
		if (aKey!=null && !aKey.equals("")) {
			SoftConfig config ;
			List<SoftConfig> list = manager.createQuery("from SoftConfig sc where sc.key=:key")
				.setParameter("key", aKey)
				.getResultList();
			if (list.isEmpty()) {
				config = new SoftConfig() ;
				config.setKey(aKey) ;
				String value=aDefault.get(aKey+":value") ;
				String description=aDefault.get(aKey+":description") ;
				if (value==null) value="" ;
				config.setKeyValue(value) ;
				config.setDescription("auto " +description) ;
				manager.persist(config) ;
			} else {
				config= list.get(0) ;
			}
			return config;
		}
		return null ;
	}
	
	private Map<String, String> create() {
		Map<String, String> map = new TreeMap<>();
        map.put("data_base_namespace:value","riams") ;
        map.put("data_base_namespace:description","Основной namespace, в котором настроено приложение") ;
        return map ;
    }
    @PersistenceContext private EntityManager manager ;
    @Resource SessionContext context ;
}
