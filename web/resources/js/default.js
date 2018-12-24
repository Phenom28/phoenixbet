function handleSubmit(args, dialog){
    if (args.validationFailed) {
        PF(dialog).jq.effect("shake", {times:5}, 100);
        PF('statusDialog').hide();
    } else {
        PF(dialog).hide();
        PF('statusDialog').hide();
    }
}