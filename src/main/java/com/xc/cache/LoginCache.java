package com.xc.cache;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by yb on 2017/4/9 0009.
 */
@Component
public class LoginCache {
	/**
	 * 有效时间(s)
	 */
	private final int TIMEOUT = 300000;//设置token效期，30分钟
	private final int WAITTIME = 30000; // 设置清理现场的等待时间
	private Map<String, Long> tokens;

	@PostConstruct
	public void init() {
		tokens = new HashMap<String, Long>();
		new Thread(new TokenThread()).start();
	}

	public void setToken(String token, long date) {
		tokens.put(token, date + TIMEOUT);
	}

	public Long getToken(String token) {
		if (StringUtils.isEmpty(token)) {
			return null;
		}
		return tokens.get(token);
	}

	public void removeToken(String token) {
		if (StringUtils.isEmpty(token)) {
			return;
		}
		tokens.remove(token);
	}

	class TokenThread implements Runnable {

		@Override
		public void run() {
			while (true) {
				try {
					for (String token : tokens.keySet()) {
						if (token != null) {
							if (new Date().getTime() > tokens.get(token)) {
								// token过期
								removeToken(token);
							}
						}
					}
					try {
						Thread.sleep(WAITTIME);
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}
