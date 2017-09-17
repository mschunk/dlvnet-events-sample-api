package dlvnet.events.sample.api

class DlvnetController {
	private Map<String,String> storage = new HashMap<String,String>();

    def events(String id) {
		// create a new event
		if("POST".equals(request.method)) {
			String eventXml = request.reader.text
			String validationError = validate(eventXml)
			if(validationError != null) {
				handleResponse(400, validationError);
			} else {
				String docId = getId(eventXml);
				if(storage.get(docId) != null) {
					handleResponse(400, "event id '${docId}' already exist. ");
					return;
				}
				storage.put(docId, eventXml);
				handleResponse(201, "Created");
			}
			return;
		}
		// update existing event
		if("PUT".equals(request.method)) {
			if(storage.get(id) == null) {
				handleResponse(400, "event id '${id}' does not exist. create the event before updating it.")
				return;
			}
			String eventXml = request.reader.text
			String validationError = validate(eventXml)
			if(validationError != null) {
				handleResponse(400, validationError);
			} else {
				storage.put(getId(eventXml), eventXml);
				handleResponse(200, "OK");
			}
			return;
		}
		// delete an event
		if("DELETE".equals(request.method)) {
			if(storage.get(id) == null) {
				handleResponse(404, "event id '${id}' does not exist.")
				return;
			}
			storage.remove(id);
			handleResponse(200, "OK")
		}
		// get event(s)
		if("GET".equals(request.method)) {
			// just a sample, can do a lot more here
			String eventXml = storage.get(id);
			if(eventXml == null) {
				handleResponse(404,"event '${id}' does not exist");
			} else {
				handleResponse(200,eventXml);
			}
		}
	}

	private handleResponse(int httpStatusCode, String body) {
		response.setStatus(httpStatusCode)
		if(body != null) render(text:body)
	}

	// perform validation of xml: null indicates event is ok, otherwise gives the error
	private String validate(String xmlString) {
		// test if xml is valid
		// code for this exists and can be plugged in
		// for the sake of an example - a stub:
		if(false) return "invalid xml document: <error of xml parser>";
		if(false) return "xml is invalid against the schema: <error of schema valiation>";
		if(false) return "xml failed additional validation: <additional validation error>";
		return null;
	}

	// extract the event ID from xml representation of an event
	private String getId(String eventXml) {
		def Events = new XmlSlurper().parseText(eventXml)
		return Events.Event[0].@ID;
	}
}