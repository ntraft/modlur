package com.ntraft.util;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;

/**
 * @author Neil Traft
 */
public final class DebuggingContentHandler implements ContentHandler {

	private final ContentHandler delegate;
	private Locator locator;

	public DebuggingContentHandler(ContentHandler delegate) {
		this.delegate = delegate;
	}

	@Override
	public void setDocumentLocator(Locator locator) {
		this.locator = locator;
		delegate.setDocumentLocator(locator);
	}

	@Override
	public void startDocument() throws SAXException {
		try {
			delegate.startDocument();
		} catch (Throwable th) {
			error("startDocument", th);
		}
	}

	@Override
	public void endDocument() throws SAXException {
		try {
			delegate.endDocument();
		} catch (Throwable th) {
			error("endDocument", th);
		}
	}

	@Override
	public void startPrefixMapping(String prefix, String uri) throws SAXException {
		try {
			delegate.startPrefixMapping(prefix, uri);
		} catch (Throwable th) {
			error("startPrefixMapping", th);
		}
	}

	@Override
	public void endPrefixMapping(String prefix) throws SAXException {
		try {
			delegate.endPrefixMapping(prefix);
		} catch (Throwable th) {
			error("endPrefixMapping", th);
		}
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes atts) throws SAXException {
		try {
			delegate.startElement(uri, localName, qName, atts);
		} catch (Throwable th) {
			error("startElement", th);
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		try {
			delegate.endElement(uri, localName, qName);
		} catch (Throwable th) {
			error("endElement", th);
		}
	}

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		try {
			delegate.characters(ch, start, length);
		} catch (Throwable th) {
			error("characters", th);
		}
	}

	@Override
	public void ignorableWhitespace(char[] ch, int start, int length) throws SAXException {
		try {
			delegate.ignorableWhitespace(ch, start, length);
		} catch (Throwable th) {
			error("ignorableWhitespace", th);
		}
	}

	@Override
	public void processingInstruction(String target, String data) throws SAXException {
		try {
			delegate.processingInstruction(target, data);
		} catch (Throwable th) {
			error("processingInstruction", th);
		}
	}

	@Override
	public void skippedEntity(String name) throws SAXException {
		try {
			delegate.skippedEntity(name);
		} catch (Throwable th) {
			error("skippedEntity", th);
		}
	}

	private void error(String event, Throwable th) {
		StringBuilder sb = new StringBuilder("Error while parsing");
		sb.append(": ").append(event);
		if (locator != null) {
			sb.append('(')
				.append(locator.getLineNumber())
				.append(':')
				.append(locator.getColumnNumber())
				.append(')');
		}
		throw new RuntimeException(sb.toString(), th);
	}
}
