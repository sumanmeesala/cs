package hudson.cli;

import hudson.model.*;
import jenkins.model.Jenkins;
import hudson.model.ListView;
import jenkins.model.GlobalConfiguration;
import javaposse.jobdsl.dsl.DslFactory;

def environment = System.getenv('env') ?: "default"
def custName = 'NONE'

def jenkins = Jenkins.instance
def viewName = '${environment}_csJobs'
def existingView = jenkins.getView(viewName)

if (existingView == null) {
   def newView = new ListView(viewName)
   jenkins.addView(newView)
   println("View '$viewName' created.")
   } else {
   println("View '$viewName' already exists.")
}

matrixJob('$environment_cs1stjob') {
    description('This is an 1st cc Job DSL job')

    configure { project ->
        project / 'logRotator' {
            numToKeep(4)
                               }
              }
              
    concurrentBuild(true)
    
    scm {
    none()
    }
    
    parameters {
      stringParam('custName',custName,'desc')
      }
      




    


def globalConfiguration = GlobalConfiguration.all().find { it.displayName == 'Mask Passwords and Regexes' }

if (globalConfiguration) {
           globalConfiguration.setMaskPasswords(true)
           globalConfiguration.setMaskPasswordsConsoleLog(true)
           globalConfiguration.save()
           
           System.setProperty("org.jenkinsci.plugins.workflow.steps.SecretEnvVarHelper.CONFIGURED", "true")
           println("Enabled passwords")
           } else {
            println("Enabled passwordsnot found")
                   }
                   
    def pName = "${custName}"

    steps {
        shell('echo ${environment} "Im Athna_cc_trigger" ${pName}')
    }

                   
myView = hudson.model.Hudson.instance.getView('$environment_csJobs')
myView.doAddJobToView('$environment_cs1stjob') 
jenkins.save()
}

