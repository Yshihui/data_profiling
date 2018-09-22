/**
 * Copyright 2015-2016 by Metanome Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.metanome.backend.resources;

import de.metanome.backend.result_postprocessing.ResultPostProcessor;
import de.metanome.backend.results_db.*;
import javax.ws.rs.core.Response;

public class ResultStoreResource {
  
  /*
   * id 第几个
   * dataInpedendent
   * */
  public static void main(String[] args) {
    Long id = Long.valueOf(args[0]);
   String dataIndependent = args[1];
  
    try {//获取基本的信息，配置，参数，输入数据
      Execution execution = (Execution) HibernateUtil.retrieve(Execution.class, id);
      
      if (dataIndependent== "false") {
        ResultPostProcessor.extractAndStoreResultsDataIndependent(execution);
      } else { //yangsh
        
        ResultPostProcessor.extractAndStoreResultsDataDependent(execution);
        }
      }catch(Exception e){
        String message = "";
        if (e.getMessage() != null) {
          message += e.getMessage();
        }
        e.printStackTrace();
        throw new WebException(message, Response.Status.BAD_REQUEST);
      }
    }
  
  }
  

