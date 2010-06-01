/*******************************************************************************
 * Copyright (c) 2010 Guillaume Gérard
 * Projet INRIA Madynes
 * 
 * This file is part of jyang.
 * 
 * jyang is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * jyang is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with jyang.  If not, see <http://www.gnu.org/licenses/>.
 *******************************************************************************/
package fr.loria.madynes.yangeditor.ssh;

import java.io.*;
import java.util.*;

import ch.ethz.ssh2.*;
import ch.ethz.ssh2.channel.*;

/**
 * Yang Netconfig Edit
 * 
 * @author Guillaume Gérard
 */
public class YangNetconfEdit extends Thread {

	private final static String _HOSTNAME_KEY = "-h";
	private final static String _HOSTNAME = "localhost";

	private final static String _PORT_KEY = "-p";
	private final static int _PORT = 1024;

	private final static String _USERNAME_KEY = "-l";
	private final static String _USERNAME = "netconf";

	private final static String _PASSWORD_KEY = "-lp";
	private final static String _PASSWORD = "netconf";

	private final static String _RSA_KEY = "-i";
	private final static String _RSA = "./conf/id_rsa";

	private final static String _PASSPHRASE_KEY = "-ip";
	private final static String _PASSPHRASE = null;

	private final static String _VERBOSE_KEY = "-v";
	private final static boolean _VERBOSE = true;

	private final static String _PROXYHOST_KEY = "-ph";
	private final static String _PROXYHOST = null;

	private final static String _PROXYPORT_KEY = "-pp";
	private final static int _PROXYPORT = 3128;

	private final static String _NODELAY_KEY = "-nd";
	private final static boolean _NODELAY = false;

	private static final String _HELLO = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
			+ "<hello xmlns=\"urn:ietf:params:xml:ns:netconf:base:1.0\">"
			+ "<capabilities>"
			+ "<capability>urn:ietf:params:netconf:base:1.0</capability>"
			+ "<capability>urn:ietf:params:xml:ns:netconf:base:1.0</capability>"
			+ "<capability>urn:ietf:params:netconf:capability:xpath:1.0</capability>"
			+ "</capabilities>" + "</hello>]]>]]>";

	private static int _COUNT = 0;

	private String hostname;
	private int port;
	private String username;
	private String password;
	private String rsa;
	private String passphrase;
	private String proxyHost;
	private int proxyPort;
	private boolean verbose;
	private boolean nodelay;
	private Connection con;
	private Session sess;

	public YangNetconfEdit(String hostname, int port, String username,
			String password, String rsa, String passphrase, boolean verbose,
			boolean nodelay) {
		super();
		this.hostname = hostname;
		this.port = port;
		this.username = username;
		this.password = password;
		this.rsa = rsa;
		this.passphrase = passphrase;
		this.verbose = verbose;
		this.nodelay = nodelay;
	}

	public String getHostname() {
		return hostname;
	}

	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRsa() {
		return rsa;
	}

	public void setRsa(String rsa) {
		this.rsa = rsa;
	}

	public String getPassphrase() {
		return passphrase;
	}

	public void setPassphrase(String passphrase) {
		this.passphrase = passphrase;
	}

	public String getProxyHost() {
		return proxyHost;
	}

	public void setProxyHost(String proxyHost) {
		this.proxyHost = proxyHost;
	}

	public int getProxyPort() {
		return proxyPort;
	}

	public void setProxyPort(int proxyPort) {
		this.proxyPort = proxyPort;
	}

	public boolean isVerbose() {
		return verbose;
	}

	public void setVerbose(boolean verbose) {
		this.verbose = verbose;
	}

	public boolean isNodelay() {
		return nodelay;
	}

	public void setNodelay(boolean nodelay) {
		this.nodelay = nodelay;
	}

	public void setProxy(String proxyHost, int proxyPort) {
		this.proxyHost = proxyHost;
		this.proxyPort = proxyPort;
	}

	public boolean isConnected() {
		return (con != null);
	}

	@Override
	public void run() {
		if (con == null) {
			File keyfile = new File(rsa);
			try {
				/* Create a connection instance */
				con = new Connection(hostname, port);

				if (proxyHost != null && proxyPort > 0 && proxyPort < 65536) {
					/* We want to connect through a HTTP proxy */
					con.setProxyData(new HTTPProxyData(proxyHost, proxyPort));
				}

				if (nodelay) {
					/* TCP NO Delay */
					con.setTCPNoDelay(true);
				}

				/* Now connect */
				con.connect();

				/* Authenticate */
				/* 1 - By the RSA key */
				/* 2 - By login & password */
				boolean isAuthenticated = false;
				if (keyfile != null) {
					isAuthenticated = con.authenticateWithPublicKey(username,
							keyfile, passphrase);
				}
				if (!isAuthenticated) {
					isAuthenticated = con.authenticateWithPassword(username,
							password);
					if (!isAuthenticated) {
						throw new IOException("Authentication failed.");
					}
				}

				sess = con.openSession();

				/* Hello Messages */
				write(_HELLO, true);

				/* Messages */
				Scanner sc = new Scanner(System.in);
				while (isConnected()) {
					String msg = new String();
					System.out
							.println("(messages ends with ']]>]]>' to be sended,");
					System.out
							.println("or ends with '!exit!' to exit the program)");
					System.out.println();
					System.out.println("Your Message :");
					while (!msg.endsWith("]]>]]>")) {
						System.out.print(" > ");
						msg += sc.nextLine().trim();
						if (msg.endsWith("!exit!")) {
							disconnect();
							System.exit(0);
						}
					}
					write(msg);
				}

				sess.waitForCondition(ChannelCondition.CLOSED
						| ChannelCondition.EOF | ChannelCondition.EXIT_STATUS,
						500);

				sess.close();
			} catch (IOException e) {
				disconnect();
				System.err
						.println("An exception occurred while trying to connect to the host: "
								+ hostname + ", Exception=" + e.getMessage());
			}
		}
	}

	public void disconnect() {
		if (con != null) {
			con.close();
			con = null;
		}
	}

	public void write(String msg, boolean readFirst) {
		try {
			if (msg == null) {
				throw new IOException("Message can't be null.");
			}
			if (msg.isEmpty()) {
				throw new IOException("Message can't be empty.");
			}
			if (!msg.endsWith("]]>]]>")) {
				throw new IOException("Message needs to ends with ']]>]]>'.");
			}

			_COUNT++;

			if (readFirst) {
				read();
			}

			System.out.println("Message sended #" + _COUNT + ":\n" + msg);
			ChannelOutputStream cos = (ChannelOutputStream) sess.getStdin();
			cos.write(msg.getBytes());
			cos.flush();

			if (!readFirst) {
				read();
			}
		} catch (IOException e) {
			disconnect();
			System.err
					.println("An exception occurred while trying to write to the session, Exception="
							+ e.getMessage());
		}
	}

	private void read() {
		try {
			int c = sess.waitForCondition(ChannelCondition.STDOUT_DATA
					| ChannelCondition.STDERR_DATA, 0);
			ChannelInputStream cis = null;
			if ((c & ChannelCondition.STDOUT_DATA) == ChannelCondition.STDOUT_DATA) {
				cis = (ChannelInputStream) sess.getStdout();
			} else {
				cis = (ChannelInputStream) sess.getStderr();
			}
			System.out.println("Message received #" + _COUNT + ":");
			byte[] buff = new byte[cis.available()];
			cis.read(buff);
			System.out.println(new String(buff));
			cis.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void write(String msg) {
		write(msg, false);
	}

	public final static String _HELP = "Usage: java YangNetConfig ["
			+ _VERBOSE_KEY + "] [" + _NODELAY_KEY + "] [" + _HOSTNAME_KEY
			+ " hostname] [" + _PORT_KEY + " port] [" + _USERNAME_KEY
			+ " username] [" + _PASSWORD_KEY + " username_password] ["
			+ _RSA_KEY + " rsa_private_key] [" + _PASSPHRASE_KEY
			+ " rsa_passphrase] [" + _PROXYHOST_KEY + " proxy_hostname] ["
			+ _PROXYPORT_KEY + " proxy_port]\n" + "\n" + "Description:\n"
			+ "\n" + _HOSTNAME_KEY + "\t:\thostname (default: " + _HOSTNAME
			+ ")\n" + _PORT_KEY + "\t:\tport (default: " + _PORT + ")\n" + "\n"
			+ _USERNAME_KEY + "\t:\tusername (default: " + _USERNAME + ")\n"
			+ _PASSWORD_KEY + "\t:\tpassword for the username (default: "
			+ _PASSWORD + ")\n" + "\n" + _RSA_KEY
			+ "\t:\tpath to the rsa private key (default: " + _RSA + ")\n"
			+ _PASSPHRASE_KEY
			+ "\t:\tpassphrase of the rsa private key (default: " + _PASSPHRASE
			+ ")\n" + "\n" + _PROXYHOST_KEY
			+ "\t:\thost for the HTTP proxy connection (default: " + _PROXYHOST
			+ ")\n" + _PROXYPORT_KEY
			+ "\t:\tport for the HTTP proxy connection (default: " + _PROXYPORT
			+ ")\n" + "\n" + _VERBOSE_KEY + "\t:\tverbose mode (default: "
			+ _VERBOSE + ")\n" + _NODELAY_KEY
			+ "\t:\tmode without TCP Delay (default: " + _NODELAY + ")\n"
			+ "\n" + "YangNetconfEdit by Guillaume Gérard";

	/**
	 * Main Program
	 * 
	 * @param args
	 *            arguments
	 */
	public static void main(String[] args) {
		String hostname = _HOSTNAME;
		int port = _PORT;

		String username = _USERNAME;
		String password = _PASSWORD;

		String rsa = _RSA;
		String passphrase = _PASSPHRASE;

		boolean verbose = _VERBOSE;

		String proxyHost = _PROXYHOST;
		int proxyPort = _PROXYPORT;

		boolean nodelay = _NODELAY;

		int i = 0;
		while (i < args.length && args[i].startsWith("-")) {
			String key = args[i];
			if (key.equals(_HOSTNAME_KEY)) {
				if (i < args.length) {
					hostname = args[i + 1];
				} else {
					System.err.println(_HOSTNAME_KEY
							+ " option require a hostname");
				}
			} else if (key.equals(_PORT_KEY)) {
				if (i < args.length) {
					port = Integer.decode(args[i + 1]);
				} else {
					System.err.println(_PORT_KEY + " option require a port");
				}
			} else if (key.equals(_USERNAME_KEY)) {
				if (i < args.length) {
					username = args[i + 1];
				} else {
					System.err.println(_USERNAME_KEY
							+ " option require an username");
				}
			} else if (key.equals(_PASSWORD_KEY)) {
				if (i < args.length) {
					password = args[i + 1];
				} else {
					System.err.println(_PASSWORD_KEY
							+ " option require a password");
				}
			} else if (key.equals(_RSA_KEY)) {
				if (i < args.length) {
					rsa = args[i + 1];
				} else {
					System.err.println(_RSA_KEY
							+ " option require a rsa private key");
				}
			} else if (key.equals(_PASSPHRASE_KEY)) {
				if (i < args.length) {
					passphrase = args[i + 1];
				} else {
					System.err
							.println(_PASSPHRASE_KEY
									+ " option require a passphrase for the rsa private key");
				}
			} else if (key.equals(_PROXYHOST_KEY)) {
				if (i < args.length) {
					proxyHost = args[i + 1];
				} else {
					System.err.println(_PROXYHOST_KEY
							+ " option require a proxy hostname");
				}
			} else if (key.equals(_PROXYPORT_KEY)) {
				if (i < args.length) {
					proxyPort = Integer.decode(args[i + 1]);
				} else {
					System.err.println(_PROXYPORT_KEY
							+ " option require a proxy port");
				}
			} else {
				if (key.equals(_VERBOSE_KEY)) {
					verbose = true;
				} else if (key.equals(_NODELAY_KEY)) {
					nodelay = true;
				} else {
					System.out.println(_HELP);
					System.err.println("Unknow " + key + " option.");
					System.exit(1);
				}
			}
			i += 2;
		}

		YangNetconfEdit yne = new YangNetconfEdit(hostname, port, username,
				password, rsa, passphrase, verbose, nodelay);
		if (proxyHost != null && !proxyHost.isEmpty()) {
			yne.setProxy(proxyHost, proxyPort);
		}
		yne.start();
	}
}
