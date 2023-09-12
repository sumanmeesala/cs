package hudson.cli;

import hudson.model.*
import hudson.matrix.*
import hudson.util.*
import jenkins.model.Jenkins
import hudson.model.ListView
import jenkins.model.GlobalConfiguration
import javaposse.jobdsl.dsl.DslFactory


def environment = env
def custName = 'NONE'

def jenkins = Jenkins.instance


def jobName = environment+'_cs2ndjob'
   
matrixJob(jobName) {

    description('This is an 2nd cc Job DSL job')


          println("job ' + $jobName + ' created-2.")
                  }
                   

jenkins.save()


