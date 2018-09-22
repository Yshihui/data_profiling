package de.metanome.backend.my;

import de.metanome.backend.resources.WebException;
import de.metanome.backend.result_postprocessing.ResultPostProcessor;
import de.metanome.backend.results_db.Execution;
import de.metanome.backend.results_db.HibernateUtil;
import de.metanome.backend.results_db.Input;
import de.metanome.backend.results_db.Result;

import javax.ws.rs.core.Response;
import java.util.Collection;
import java.util.Set;

public class Main {
	public static void main(String[] args) {
		Set<Result> results=args[0];
		Collection<Input> inputs= args[1]
		
		try {//获取基本的信息，配置，参数，输入数据
			Execution execution = (Execution) HibernateUtil.retrieve(Execution.class, id);
			ResultPostProcessor.extractAndStoreResultsDataDependent(results,inputs);
		} catch (Exception e) {
			String message = "";
			if (e.getMessage() != null) {
				message += e.getMessage();
			}
			e.printStackTrace();
			throw new WebException(message, Response.Status.BAD_REQUEST);
		}
	}

}
