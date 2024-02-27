package com.ej.hgj.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.net.InetAddress;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

public class UniqID {
    private static final Log log = LogFactory.getLog(UniqID.class);
    private static char[] digits = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    private static UniqID me = new UniqID();
    private String hostAddr;
    private Random random = new SecureRandom();
    private MessageDigest mHasher;
    private UniqID.UniqTimer timer = new UniqID.UniqTimer();

    private UniqID() {
        try {
            InetAddress addr = InetAddress.getLocalHost();
            this.hostAddr = addr.getHostAddress();
        } catch (IOException var3) {
            log.error("[UniqID] Get HostAddr Error", var3);
            this.hostAddr = String.valueOf(System.currentTimeMillis());
        }

        if (StringUtils.isBlank(this.hostAddr) || "127.0.0.1".equals(this.hostAddr)) {
            this.hostAddr = String.valueOf(System.currentTimeMillis());
        }

        if (log.isDebugEnabled()) {
            log.debug("[UniqID]hostAddr is:" + this.hostAddr);
        }

        try {
            this.mHasher = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException var2) {
            this.mHasher = null;
            log.error("[UniqID]new MD% Hasher error", var2);
        }

    }

    public static UniqID getInstance() {
        return me;
    }

    public String getUniqID() {
        StringBuffer sb = new StringBuffer();
        long t = this.timer.getCurrentTime();
        sb.append(t);
        sb.append("-");
        sb.append(this.random.nextInt(8999) + 1000);
        sb.append("-");
        sb.append(this.hostAddr);
        sb.append("-");
        sb.append(Thread.currentThread().hashCode());
        if (log.isDebugEnabled()) {
            log.debug("[UniqID.getUniqID]" + sb.toString());
        }

        return sb.toString();
    }

    public String getUniqIDHash() {
        String id = this.getUniqID();
        if (this.mHasher != null) {
            synchronized(this.mHasher) {
                byte[] bt = this.mHasher.digest(id.getBytes());
                int l = bt.length;
                char[] out = new char[l << 1];
                int i = 0;

                for(int var7 = 0; i < l; ++i) {
                    out[var7++] = digits[(240 & bt[i]) >>> 4];
                    out[var7++] = digits[15 & bt[i]];
                }

                if (log.isDebugEnabled()) {
                    log.debug("[UniqID.getuniqIDHash]" + new String(out));
                }

                return new String(out);
            }
        } else {
            return id;
        }
    }

    private class UniqTimer {
        private long lastTime;

        private UniqTimer() {
            this.lastTime = System.currentTimeMillis();
        }

        public synchronized long getCurrentTime() {
            long currTime = System.currentTimeMillis();
            this.lastTime = Math.max(this.lastTime + 1L, currTime);
            return this.lastTime;
        }
    }
}
