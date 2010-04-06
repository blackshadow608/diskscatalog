package image.grabber;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.SwingWorker;

/**
 * Граббер, который выполняет загрузку и сохранение изображений.
 * 
 * @author Alexander Levin
 */
public class Grabber {

	/*
	 * Список слушаталей граббера.
	 */
	private List<IGrabberListener> listeners = new ArrayList<IGrabberListener>();

	private String pageUrl;
	private String folderName;

	private BackgroundExecutor backExe;

	private int totalImages = 0;
	private int procImages = 0;

	private String console;

	public Grabber() {

	}

	public String getProcText() {
		return totalImages + "/" + procImages;
	}

	public String getConcole() {
		return console;
	}

	/**
	 * Запускает процесс загрузки и сохранения изображений.
	 * 
	 * @param pageUrl
	 *            - адрес страницы, с которой начинается процесс загрузки и
	 *            сохранения изображений.
	 * @param folderName
	 *            - имя папки, в которую будут сохраняться все загруженные
	 *            изображения.
	 */
	public void start(String pageUrl, String folderName) {
		this.pageUrl = pageUrl;
		this.folderName = folderName;
		fireStart();
		console = "";
		backExe = new BackgroundExecutor();
		backExe.execute();
	}

	public void stop() {
		backExe.cancel(true);
		fireStop();
	}

	public void addListener(IGrabberListener listener) {
		if (!listeners.contains(listener)) {
			listeners.add(listener);
		}
	}

	public void removeListener(IGrabberListener listener) {
		if (listeners.contains(listener)) {
			listeners.remove(listener);
		}
	}

	/**
	 * Информирует слушателей о том, что произошел запуск процесса загрузеи и
	 * сохранения изображений.
	 */
	public void fireStart() {
		for (IGrabberListener listener : listeners) {
			listener.started(this);
		}
	}

	public void fireStop() {
		for (IGrabberListener listener : listeners) {
			listener.stopped(this);
		}
	}

	public void fireChanging() {
		for (IGrabberListener listener : listeners) {
			listener.stateChanged(this);
		}
	}

	public void fireError(String message) {
		for (IGrabberListener listener : listeners) {
			listener.error(this, message);
		}
	}

	private List<String> getPageLinks(Page page) {
		List<String> links = new ArrayList<String>();

		/*
		 * Пробегаемся по всем ссылкам документа.
		 */

		int pos = 0;

		while (true) {
			pos = page.getNextAPos(pos);

			if (pos < 0) {
				break;
			}

			if (linkIsValid(page, pos)) {
				String link = getLinkHref(page, pos);
				if (link != null && !links.contains(link)) {
					links.add(link);
				}
			}

			pos++;
		}

		return links;
	}

	private String getLinkHref(Page page, int pos) {
		String href = page.getContent().substring(pos + 9, pos + 25);
		href = "http://www.kinopoisk.ru" + href;
		return href;
	}

	private boolean linkIsValid(Page page, int pos) {
		String etalon = "<a href=\"/picture";
		if (etalon.equals(page.getContent().substring(pos, pos + 17))) {
			return true;
		}
		return false;
	}

	private String getImageUrl(Page page) {
		int pos = page.getImgPos(9);
		int beginIndex = page.getContent().indexOf("http", pos);
		int endIndex = page.getContent().indexOf("\'", beginIndex);
		String imageUrl = page.getContent().substring(beginIndex, endIndex);
		return imageUrl;
	}

	private void saveImage(String imageUrl, File file)
			throws MalformedURLException, IOException {
		BufferedImage img = ImageIO.read(new URL(imageUrl));

		String filmFolderName = "images/" + folderName;
		File filmFolder = new File(filmFolderName);

		if (!filmFolder.exists()) {
			filmFolder.mkdir();
		}

		ImageIO.write(img, "jpg", file);
	}

	private String getImageFileName(String imageUrl) {
		int beginIndex = imageUrl.length() - 11;
		int endIndex = imageUrl.length();
		String fileName = imageUrl.substring(beginIndex, endIndex);
		return fileName;
	}

	public File getImageFile(String fileName) {
		File outputfile = new File("images/" + folderName + "/" + fileName);
		return outputfile;
	}

	protected void done() {
		fireStop();
	}

	/**
	 * Обеспечивает фоновое выполнение продолжительной задачи в отдельном
	 * потоке.
	 * 
	 * @author Alexander Levin
	 */
	private class BackgroundExecutor extends SwingWorker<String, Object> {

		protected String doInBackground() throws Exception {

			try {
				/*
				 * Загружаем страницу.
				 */
				Page page = PageLoader.loadPage(pageUrl);
				log("First page loading\n");

				/*
				 * Получить рисунок со страницы и сохранить его.
				 */
				String imageUrl = getImageUrl(page);
				String fileName = getImageFileName(imageUrl);
				File file = getImageFile(fileName);
				if (!file.exists()) {
					saveImage(imageUrl, file);
				}

				procImages++;

				log("First image saved\n");

				/*
				 * Получить ссылки на другие страницы с рисунками из галереи и
				 * пробежаться по ним.
				 */
				List<String> links = getPageLinks(page);

				totalImages = links.size() + 1;

				fireChanging();

				log("Getting all links list\n");
				for (String link : links) {

					if (isCancelled()) {
						break;
					}

					log("Page loading " + link + "\n");
					page = PageLoader.loadPage(link);
					imageUrl = getImageUrl(page);
					fileName = getImageFileName(imageUrl);
					file = getImageFile(fileName);

					if (!file.exists()) {
						log("Image saving " + imageUrl + "\n");
						saveImage(imageUrl, file);
					}

					procImages++;
					fireChanging();
				}

				totalImages = 0;
				procImages = 0;

				fireChanging();

				fireStop();

			} catch (Exception e) {
				fireError("Problems with page loading!");				
				ByteArrayOutputStream out = new ByteArrayOutputStream();
				e.printStackTrace(new PrintStream(out));
				log(new String(out.toByteArray()));
				
			}

			return null;
		}

	}

	private void log(String message) {
		console += message;
	}
}
